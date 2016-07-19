package de.rocketlabs.behatide.application.keymanager.listener;

import de.rocketlabs.behatide.application.keymanager.KeyManagerListener;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.jetbrains.annotations.NotNull;

public class TabListener implements KeyManagerListener {

    private CodeArea codeArea;
    public TabListener(@NotNull CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    @Override
    public void handleEvent(KeyEvent event)
    {
        event.consume();
        boolean inverted = event.isShiftDown();
        int caretPosition = codeArea.getCaretPosition();
        IndexRange selection = codeArea.getSelection();
        if (selection.getLength() == 0) {
            if (inverted) {
                codeArea.replaceText(caretPosition - 2, caretPosition, "");
            } else {
                codeArea.insertText(caretPosition, "  ");
            }
            return;
        }

        int startPosition = selection.getStart();
        while (startPosition > 0) {
            char c = codeArea.getText().charAt(--startPosition);
            if (isNewlineCharacter(c)) {
                break;
            }
        }

        String content = codeArea.getText().substring(startPosition, selection.getEnd());

        int newStart;
        int newEnd;
        if (inverted) {
            codeArea.replaceText(startPosition, selection.getEnd(), content.replaceAll("\\n ( )?", "\n"));
            newStart = selection.getStart()  -2;
            newEnd = selection.getEnd() + (-2 * countOccurrences(content, '\n'));
        } else {
            codeArea.replaceText(startPosition, selection.getEnd(), content.replaceAll("\\n", "\n  "));
            newStart = selection.getStart() + 2;
            newEnd = selection.getEnd() + (2 * countOccurrences(content, '\n'));
        }
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

    private int countOccurrences(String haystack, char needle)
    {
        int count = 0;
        for (int i=0; i < haystack.length(); i++)
        {
            if (haystack.charAt(i) == needle)
            {
                count++;
            }
        }
        return count;
    }
}
