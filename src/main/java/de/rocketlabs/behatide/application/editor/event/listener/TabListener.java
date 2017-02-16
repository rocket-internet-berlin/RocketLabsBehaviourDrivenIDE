package de.rocketlabs.behatide.application.editor.event.listener;

import de.rocketlabs.behatide.application.editor.component.Editor;
import de.rocketlabs.behatide.application.editor.component.EditorBehavior;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyEvent;

public class TabListener {

    public static void handleEvent(EditorBehavior behavior, KeyEvent event) {
        Editor editor = behavior.getEditor();
        event.consume();
        boolean inverted = event.isShiftDown();
        int caretPosition = editor.getCaretPosition();
        IndexRange selection = editor.getSelection();
        if (selection.getLength() == 0) {
            handleSingleLine(editor, inverted, caretPosition);
        } else {
            handleMultipleLines(editor, inverted, caretPosition, selection, getNewStartPosition(editor, selection));
        }
    }

    private static void handleSingleLine(Editor editor, boolean inverted, int caretPosition) {
        if (inverted) {
            editor.replaceText(caretPosition - 2, caretPosition, "");
        } else {
            editor.insertText(caretPosition, "  ");
        }
    }

    private static void handleMultipleLines(Editor editor,
                                            boolean inverted,
                                            int caretPosition,
                                            IndexRange selection,
                                            int startPosition) {
        String content = editor.getText().substring(startPosition, selection.getEnd());
        String searchFor = inverted ? "\\n ( )?" : "\\n";
        String replaceWith = inverted ? "\n" : "\n  ";
        int spaceAmount = inverted ? -2 : 2;
        int newStart = selection.getStart() + spaceAmount;
        int newEnd = selection.getEnd() + (spaceAmount * countOccurrences(content, '\n'));

        editor.replaceText(startPosition, selection.getEnd(), content.replaceAll(searchFor, replaceWith));
        editor.selectRange(newStart, newEnd);
        if (caretPosition == selection.getStart()) {
            editor.positionCaret(newStart);
        } else {
            editor.positionCaret(newEnd);
        }
    }

    private static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    private static int getNewStartPosition(Editor editor, IndexRange selection) {
        int startPosition = selection.getStart();
        while (startPosition > 0) {
            char c = editor.getText().charAt(--startPosition);
            if (c == '\r' || c == '\n') {
                break;
            }
        }
        return startPosition;
    }
}
