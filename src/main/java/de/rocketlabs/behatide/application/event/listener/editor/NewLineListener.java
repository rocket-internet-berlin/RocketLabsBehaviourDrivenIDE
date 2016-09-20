package de.rocketlabs.behatide.application.event.listener.editor;

import com.google.common.base.Strings;
import de.rocketlabs.behatide.application.component.editor.Editor;
import de.rocketlabs.behatide.application.component.editor.EditorBehavior;
import javafx.scene.input.KeyEvent;

public class NewLineListener {

    private static final char NEW_LINE_CHARACTER = '\n';

    public static void handleEvent(EditorBehavior behavior, KeyEvent event) {
        Editor editor = behavior.getEditor();
        String str = editor.getText().substring(0, editor.getCaretPosition());
        int lastLineBreak;
        if (editor.getLineIndex() > 0) {
            lastLineBreak = Math.min(str.lastIndexOf(NEW_LINE_CHARACTER) + 1, str.length());
        } else {
            lastLineBreak = 0;
        }
        String lastLine = str.substring(lastLineBreak);
        Integer nextIndent = countLeadingOccurrences(lastLine, ' ');
        String s = NEW_LINE_CHARACTER + Strings.repeat(" ", Math.max(0, nextIndent));
        editor.insertText(editor.getCaretPosition(), s);

        event.consume();
    }

    private static int countLeadingOccurrences(String haystack, char needle) {
        int count = 0;
        while (count < haystack.length() && haystack.charAt(count) == needle) {
            count++;
        }
        return count;
    }
}
