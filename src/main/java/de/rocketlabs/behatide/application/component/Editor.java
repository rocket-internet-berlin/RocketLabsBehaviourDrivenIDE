package de.rocketlabs.behatide.application.component;

import de.rocketlabs.behatide.application.keymanager.KeyManager;
import de.rocketlabs.behatide.application.keymanager.listener.NewLineListener;
import de.rocketlabs.behatide.application.keymanager.listener.TabListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Editor extends VBox
{
    private static final String[] BEHAT_KEYWORDS = new String[] {
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
            "    Then the character should appear on the screen");


    private static final Set<String> CSS_CLASS_KEYWORD = Collections.singleton("keyword");
    private static final Set<String> CSS_CLASS_DEFAULT = Collections.singleton("default");

    private KeyManager keyManager;

    @FXML
    public CodeArea codeArea;

    public Editor() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Editor.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            registerKeyEvents();

            keyManager = new KeyManager();
            keyManager.addMap(KeyCode.TAB,  new TabListener(codeArea));
            keyManager.addMap(KeyCode.ENTER,  new NewLineListener(codeArea));

            setDesignToCodeArea();
            addEventsToCodeArea();

        } catch (IOException exception) {
          throw new RuntimeException(exception);
        }
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text)
    {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
            spansBuilder.add(CSS_CLASS_DEFAULT, matcher.start() - lastKwEnd);
            spansBuilder.add(CSS_CLASS_KEYWORD, matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(CSS_CLASS_DEFAULT, text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private Editor setDesignToCodeArea()
    {
        IntFunction<String> format = (digits -> "%"+ (digits < 2 ? 2 : digits) + "d");
        LineNumber ln = new LineNumber(codeArea.getParagraphs(), format);

        codeArea.setParagraphGraphicFactory(ln);
        codeArea.getStyleClass().add("editor-test-class");
        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
            .subscribe(change -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        codeArea.replaceText(0, 0, EDITOR_CODE);
        codeArea.setPrefSize(1000, 1000);

        return this;
    }

    private Editor addEventsToCodeArea()
    {
        codeArea.setOnKeyPressed(e -> {
            keyManager.fireKeyMapEvent(e);
        });

        return this;
    }

    private void registerKeyEvents()
    {
//        KeyMapHandler.addListener(TabEvent.class, new TabListener());
//        KeyMapHandler.addListener(NewLineEvent.class, new NewLineListener());
    }
}
