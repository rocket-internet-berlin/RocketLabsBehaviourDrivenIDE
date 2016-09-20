package de.rocketlabs.behatide.application.component.editor;

import de.rocketlabs.behatide.application.event.DefinitionClickedEvent;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.event.FileOpenEvent;
import de.rocketlabs.behatide.application.event.FileSaveRequestEvent;
import de.rocketlabs.behatide.application.event.listener.editor.*;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.skin.StyledTextAreaBehavior;
import org.fxmisc.richtext.skin.StyledTextAreaVisual;
import org.fxmisc.wellbehaved.event.EventHandlerHelper;
import org.fxmisc.wellbehaved.event.EventHandlerTemplate;

import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import static javafx.scene.input.KeyCombination.SHIFT_ANY;
import static org.fxmisc.wellbehaved.event.EventPattern.keyPressed;

public class EditorBehavior extends StyledTextAreaBehavior {

    private final Editor editor;

    private final static EventHandlerTemplate<EditorBehavior, ? super KeyEvent> KEY_PRESSED_TEMPLATE;

    static {
        KEY_PRESSED_TEMPLATE = EventHandlerTemplate
            .on(keyPressed(ENTER)).act(NewLineListener::handleEvent)
            .on(keyPressed(TAB, SHIFT_ANY)).act(TabListener::handleEvent)
            .create()
            .onlyWhen(b -> b.editor.isEditable());
    }

    EditorBehavior(StyledTextAreaVisual<?> visual) {
        super(visual);
        editor = (Editor) visual.getControl();

        EventHandler<? super KeyEvent> keyPressedHandler = KEY_PRESSED_TEMPLATE.bind(this);

        EventHandlerHelper.install(editor.onKeyPressedProperty(), keyPressedHandler);
        editor.focusedProperty().addListener(this::focusEvent);
        EventManager.addListener(FileOpenEvent.class, new FileOpenEventListener(editor));
        EventManager.addListener(DefinitionClickedEvent.class, new DefinitionClickedEventListener(editor));
        EventManager.addListener(FileSaveRequestEvent.class, new SaveFileListener(editor));
    }

    public Editor getEditor() {
        return editor;
    }

    private void focusEvent(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            onFocusGained();
        } else {
            onFocusLost();
        }
    }

    private void onFocusGained() {
        //do nothing (for now)
    }

    private void onFocusLost() {
        EventManager.fireEvent(new FileSaveRequestEvent(editor.getProject()));
    }
}
