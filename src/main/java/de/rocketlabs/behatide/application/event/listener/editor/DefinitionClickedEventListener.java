package de.rocketlabs.behatide.application.event.listener.editor;

import de.rocketlabs.behatide.application.component.editor.Editor;
import de.rocketlabs.behatide.application.event.DefinitionClickedEvent;
import de.rocketlabs.behatide.application.event.EventListener;

public class DefinitionClickedEventListener implements EventListener<DefinitionClickedEvent> {

    private Editor editor;

    public DefinitionClickedEventListener(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void handleEvent(DefinitionClickedEvent event) {
        if (event.getProject().equals(editor.getProject())) {
            editor.insertLine();
            editor.insertTextAtCaret("And " + event.getDefinition().getAnnotations().get(0).getStatement());
        }
    }

    @Override
    public boolean runOnJavaFxThread() {
        return true;
    }
}
