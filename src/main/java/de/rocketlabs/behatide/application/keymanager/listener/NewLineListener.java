package de.rocketlabs.behatide.application.keymanager.listener;

import com.google.common.base.Strings;
import de.rocketlabs.behatide.application.keymanager.KeyEventListener;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.jetbrains.annotations.NotNull;

public class NewLineListener implements KeyEventListener {

    private static final char NEW_LINE_CHARACTER = '\n';

    private CodeArea codeArea;

    public NewLineListener(@NotNull CodeArea codeArea) {
        this.codeArea = codeArea;
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

    @Override
    public void handleEvent(KeyEvent event) {
        event.consume();
        String str = codeArea.getText().substring(0, codeArea.getCaretPosition());
        String lastLine = str.substring(str.lastIndexOf(NEW_LINE_CHARACTER));
        Integer nextIndent = countOccurrences(lastLine, ' ');
        for (int value : lastLine.getBytes()) {
            if (value == NEW_LINE_CHARACTER) {
                nextIndent++;
            } else {
                break;
            }
        }
        String s = NEW_LINE_CHARACTER + Strings.repeat(" ", nextIndent - 1);
        codeArea.insertText(codeArea.getCaretPosition(), s);
    }
}
