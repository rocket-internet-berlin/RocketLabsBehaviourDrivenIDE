package de.rocketlabs.behatide.application.editor.component;

import de.rocketlabs.behatide.application.action.ActionRunner;
import de.rocketlabs.behatide.application.editor.action.SaveFile;
import de.rocketlabs.behatide.application.editor.event.listener.DefinitionClickedEventListener;
import de.rocketlabs.behatide.application.editor.event.listener.NewLineListener;
import de.rocketlabs.behatide.application.editor.event.listener.TabListener;
import de.rocketlabs.behatide.application.event.DefinitionClickedEvent;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.model.ProjectContext;
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
    private final ProjectContext projectContext;

    private final static EventHandlerTemplate<EditorBehavior, ? super KeyEvent> KEY_PRESSED_TEMPLATE;

    static {
        KEY_PRESSED_TEMPLATE = EventHandlerTemplate
            .on(keyPressed(ENTER)).act(NewLineListener::handleEvent)
            .on(keyPressed(TAB, SHIFT_ANY)).act(TabListener::handleEvent)
            .create()
            .onlyWhen(b -> b.editor.isEditable());
    }

    EditorBehavior(StyledTextAreaVisual<?> visual, ProjectContext projectContext) {
        super(visual);
        editor = (Editor) visual.getControl();
        this.projectContext = projectContext;

        EventHandler<? super KeyEvent> keyPressedHandler = KEY_PRESSED_TEMPLATE.bind(this);

        EventHandlerHelper.install(editor.onKeyPressedProperty(), keyPressedHandler);
        editor.focusedProperty().addListener(this::focusEvent);
        EventManager.addListener(DefinitionClickedEvent.class, new DefinitionClickedEventListener(editor));
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
        ActionRunner.run(new SaveFile(projectContext));
    }
}
