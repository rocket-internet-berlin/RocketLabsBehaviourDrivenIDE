package de.rocketlabs.behatide.application.keymanager.listener;

import de.rocketlabs.behatide.application.keymanager.KeyEventListener;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.jetbrains.annotations.NotNull;

public class TabListener implements KeyEventListener {

    private CodeArea codeArea;

    public TabListener(@NotNull CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public void handleEvent(KeyEvent event) {
        event.consume();
        boolean inverted = event.isShiftDown();
        int caretPosition = codeArea.getCaretPosition();
        IndexRange selection = codeArea.getSelection();
        if (selection.getLength() == 0) {
            handleSingleLine(inverted, caretPosition);
        } else {
            handleMultipleLines(inverted, caretPosition, selection, getNewStartPosition(selection));
        }
    }

    private void handleSingleLine(boolean inverted, int caretPosition) {
        if (inverted) {
            codeArea.replaceText(caretPosition - 2, caretPosition, "");
        } else {
            codeArea.insertText(caretPosition, "  ");
        }
    }

    private void handleMultipleLines(boolean inverted, int caretPosition, IndexRange selection, int startPosition) {
        String content = codeArea.getText().substring(startPosition, selection.getEnd());
        String searchFor = inverted ? "\\n ( )?" : "\\n";
        String replaceWith = inverted ? "\n" : "\n  ";
        int spaceAmount = inverted ? -2 : 2;
        int newStart = selection.getStart() + spaceAmount;
        int newEnd = selection.getEnd() + (spaceAmount * countOccurrences(content, '\n'));

        codeArea.replaceText(startPosition, selection.getEnd(), content.replaceAll(searchFor, replaceWith));
        codeArea.selectRange(newStart, newEnd);
        if (caretPosition == selection.getStart()) {
            codeArea.positionCaret(newStart);
        } else {
            codeArea.positionCaret(newEnd);
        }
    }

    private boolean isNewlineCharacter(char c) {
        return c == '\r' || c == '\n';
    }

    private int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    private int getNewStartPosition(IndexRange selection) {
        int startPosition = selection.getStart();
        while (startPosition > 0) {
            char c = codeArea.getText().charAt(--startPosition);
            if (isNewlineCharacter(c)) {
                break;
            }
        }
        return startPosition;
    }
}
