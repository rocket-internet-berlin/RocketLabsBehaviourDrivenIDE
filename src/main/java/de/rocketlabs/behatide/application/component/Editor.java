package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.keymanager.listener.NewLineListener;
import de.rocketlabs.behatide.application.keymanager.listener.TabListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventHandlerHelper;

import java.io.IOException;
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

public class Editor extends VBox {

    private static final CharSequence[] BEHAT_KEYWORDS = new String[]{
        "Feature", "Scenario Outline", "Scenario Template", "Scenario", "Examples",
        "Scenarios", "When", "Then", "Given", "And"
    };

    private static final String KEYWORD_REGEX = "(" + String.join("|", BEHAT_KEYWORDS) + ")";
    private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_REGEX + ")");
    private static final String EDITOR_CODE = String.join("\n", "Feature: Setup_Text",
                                                          "",
                                                          "  Scenario: Behat IDE-editor Text Area",
                                                          "    When I start the application",
                                                          "    Then A new Stage gets created",
                                                          "    And I see a editor window",
                                                          "    When I click on the x of the Window",
                                                          "    Then The the window gets closed",
                                                          "    When Type any character",
                                                          "    Then the character should appear on the screen"
    );

    private static final Set<String> CSS_CLASS_KEYWORD = Collections.singleton("keyword");
    private static final Set<String> CSS_CLASS_DEFAULT = Collections.singleton("default");

    @FXML
    public CodeArea codeArea;

    public Editor() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Editor.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            registerKeyCombinations();

            setDesignToCodeArea();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setDesignToCodeArea() {
        IntFunction<String> format = (digits -> "%" + (digits < 2 ? 2 : digits) + "d");
        LineNumber ln = new LineNumber(codeArea.getParagraphs(), format);

        codeArea.setParagraphGraphicFactory(ln);
        codeArea.getStyleClass().add("editor-test-class");
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        codeArea.replaceText(0, 0, EDITOR_CODE);
        codeArea.setPrefSize(1000, 1000);
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

    private void registerKeyCombinations() {
        TabListener tabListener = new TabListener(codeArea);
        registerKeyCombination(tabListener::handleEvent, TAB, SHIFT_ANY);

        NewLineListener enterListener = new NewLineListener(codeArea);
        registerKeyCombination(enterListener::handleEvent, ENTER);
    }

    private void registerKeyCombination(Consumer<? super KeyEvent> action, KeyCode code, Modifier... modifiers) {
        EventHandlerHelper.install(
            codeArea.onKeyPressedProperty(),
            EventHandlerHelper.on(keyPressed(code, modifiers)).act(action).create()
        );
    }
}
