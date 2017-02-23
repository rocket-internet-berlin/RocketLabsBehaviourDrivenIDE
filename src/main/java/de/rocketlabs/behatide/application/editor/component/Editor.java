package de.rocketlabs.behatide.application.editor.component;

import de.rocketlabs.behatide.application.component.LineNumber;
import de.rocketlabs.behatide.application.model.ProjectContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Skin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.richtext.skin.StyledTextAreaVisual;
import org.fxmisc.wellbehaved.skin.Skins;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editor extends CodeArea {

    private static final String COMMENT_PATTERN = "[\\^\n]\\s*#[^\n]*";
    private static final CharSequence[] BEHAT_KEYWORDS = new String[]{
        "Feature:", "Scenario Outline:", "Scenario Template:", "Scenario:", "Examples:",
        "Scenarios:", "When", "Then", "Given", "And", "Background:"
    };
    private static final Pattern PATTERN = Pattern.compile(
        "(?<KEYWORD>(" + String.join("|", BEHAT_KEYWORDS) + "))"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final Set<String> CSS_CLASS_KEYWORD = Collections.singleton("keyword");
    private static final Set<String> CSS_CLASS_DEFAULT = Collections.singleton("default");
    private static final Set<String> CSS_CLASS_COMMENT = Collections.singleton("comment");

    private ObjectProperty<ProjectContext> projectContext = new SimpleObjectProperty<>();
    private Path openFilePath;

    public Editor() {
        setDesign();
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            Set<String> cssClass =
                matcher.group("KEYWORD") != null ? CSS_CLASS_KEYWORD :
                    matcher.group("COMMENT") != null ? CSS_CLASS_COMMENT :
                        CSS_CLASS_DEFAULT;
            spansBuilder.add(CSS_CLASS_DEFAULT, matcher.start() - lastKwEnd);
            spansBuilder.add(cssClass, matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(CSS_CLASS_DEFAULT, text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    public int getLineIndex() {
        return offsetToPosition(getCaretPosition(), Bias.Forward).getMajor();
    }

    public Path getOpenFilePath() {
        return openFilePath;
    }

    public void setOpenFilePath(Path openFilePath) {
        this.openFilePath = openFilePath;
    }

    public ProjectContext getProjectContext() {
        return projectContext.get();
    }

    public void setProjectContext(ProjectContext projectContext) {
        this.projectContext.set(projectContext);
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

    @FXML
    public ObjectProperty<ProjectContext> projectContextProperty() {
        return projectContext;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        //noinspection RedundantTypeArguments
        return Skins.<Editor, StyledTextAreaVisual<Collection<String>>>createSimpleSkin(
            this,
            area -> new StyledTextAreaVisual<>(
                area,
                (text, styleClasses) -> text.getStyleClass().addAll(styleClasses)
            ),
            (visual) -> new EditorBehavior(visual, getProjectContext())
        );
    }

    private void setDesign() {
        IntFunction<String> format = (digits -> "%" + (digits < 2 ? 2 : digits) + "d");
        LineNumber ln = new LineNumber(getParagraphs(), format);

        setParagraphGraphicFactory(ln);
        getStyleClass().add("editor-test-class");
        richChanges()
            .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
            .subscribe(change -> setStyleSpans(0, computeHighlighting(getText())));
    }
}
