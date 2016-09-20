package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.event.DefinitionClickedEvent;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileOpenEvent;
import de.rocketlabs.behatide.application.event.FileSaveRequestEvent;
import de.rocketlabs.behatide.application.event.listener.editor.DefinitionClickedEventListener;
import de.rocketlabs.behatide.application.event.listener.editor.FileOpenEventListener;
import de.rocketlabs.behatide.application.event.listener.editor.SaveFileListener;
import de.rocketlabs.behatide.application.keymanager.listener.NewLineListener;
import de.rocketlabs.behatide.application.keymanager.listener.TabListener;
import de.rocketlabs.behatide.domain.model.Project;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventHandlerHelper;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

public class Editor extends CodeArea {

    private static final CharSequence[] BEHAT_KEYWORDS = new String[]{
        "Feature:", "Scenario Outline:", "Scenario Template:", "Scenario:", "Examples:",
        "Scenarios:", "When", "Then", "Given", "And", "Background:"
    };

    private static final String KEYWORD_REGEX = "(" + String.join("|", BEHAT_KEYWORDS) + ")";
    private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_REGEX + ")");

    private static final Set<String> CSS_CLASS_KEYWORD = Collections.singleton("keyword");
    private static final Set<String> CSS_CLASS_DEFAULT = Collections.singleton("default");

    private ObjectProperty<Project> project = new SimpleObjectProperty<>();
    private Path openFilePath;

    public Editor() {
        setUpBehavior();
        setDesignToCodeArea();
        registerEventListeners();
    }

    private void registerEventListeners() {
        EventManager.addListener(FileOpenEvent.class, new FileOpenEventListener(this));
        EventManager.addListener(DefinitionClickedEvent.class, new DefinitionClickedEventListener(this));
        EventManager.addListener(FileSaveRequestEvent.class, new SaveFileListener(this));
    }

    private void setDesignToCodeArea() {
        IntFunction<String> format = (digits -> "%" + (digits < 2 ? 2 : digits) + "d");
        IntFunction<Node> ln = LineNumberFactory.get(this, format);

        setParagraphGraphicFactory(ln);
        getStyleClass().add("editor-test-class");
        richChanges()
            .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
            .subscribe(change -> setStyleSpans(0, computeHighlighting(getText())));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            spansBuilder.add(CSS_CLASS_DEFAULT, matcher.start() - lastKwEnd);
            spansBuilder.add(CSS_CLASS_KEYWORD, matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(CSS_CLASS_DEFAULT, text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private void setUpBehavior() {
        TabListener tabListener = new TabListener(this);
        registerKeyCombination(tabListener::handleEvent, TAB, SHIFT_ANY);

        NewLineListener enterListener = new NewLineListener(this);
        registerKeyCombination(enterListener::handleEvent, ENTER);

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                EventManager.fireEvent(new FileSaveRequestEvent(getProject()));
            }
        });
    }

    private void registerKeyCombination(Consumer<? super KeyEvent> action,
                                        KeyCode code,
                                        KeyCombination.Modifier... modifiers) {
        EventHandlerHelper.install(
            onKeyPressedProperty(),
            EventHandlerHelper.on(keyPressed(code, modifiers)).act(action).create()
        );
    }

    public int getLineIndex() {
        return offsetToPosition(getCaretPosition(), Bias.Forward).getMajor();
    }

    public Project getProject() {
        return project.get();
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public void insertLine() {
        int caretPosition = getCaretPosition();
        int length = getLength();
        while (caretPosition < length && !getText(caretPosition, caretPosition + 1).equals("\n")) {
            caretPosition++;
        }
        moveTo(caretPosition);
        fireEvent(new KeyEvent(this, this, KeyEvent.KEY_PRESSED, "\n", "", KeyCode.ENTER, false, false, false, false));
    }

    public void insertTextAtCaret(String text) {
        insertText(getCaretPosition(), text);
    }

    public ObjectProperty<Project> projectProperty() {
        return project;
    }

    public void setOpenFilePath(Path openFilePath) {
        this.openFilePath = openFilePath;
    }

    public Path getOpenFilePath() {
        return openFilePath;
    }
}
