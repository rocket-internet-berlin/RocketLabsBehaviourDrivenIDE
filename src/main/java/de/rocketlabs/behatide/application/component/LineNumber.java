package de.rocketlabs.behatide.application.component;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.reactfx.collection.LiveList;
import org.reactfx.value.Val;

import java.util.function.IntFunction;

public class LineNumber implements IntFunction<Node> {

    private static final Insets DEFAULT_INSETS = new Insets(0.0, 5.0, 0.0, 5.0);
    private static final Font DEFAULT_FONT =
            Font.font("monospace", FontPosture.ITALIC, 13);

    private final Val<Integer> nParagraphs;
    private final IntFunction<String> format;

    public LineNumber(
            ObservableList size,
            IntFunction<String> format) {
        nParagraphs = LiveList.sizeOf(size);
        this.format = format;
    }

    @Override
    public Node apply(int idx) {
        Val<String> formatted = nParagraphs.map(n -> format(idx + 1, n));

        Label lineNo = new Label();
        lineNo.setFont(DEFAULT_FONT);
        lineNo.setPadding(DEFAULT_INSETS);
        lineNo.getStyleClass().add("lineNumber");

        lineNo.textProperty().bind(formatted.conditionOnShowing(lineNo));

        return lineNo;
    }

    private String format(int x, int max) {
        int digits = (int) Math.floor(Math.log10(max)) + 1;
        return String.format(format.apply(digits), x);
    }
}
