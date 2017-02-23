package de.rocketlabs.behatide.application.component.control;

import de.rocketlabs.behatide.application.event.DefinitionClickedEvent;
import de.rocketlabs.behatide.application.event.EventManager;
import de.rocketlabs.behatide.application.model.ProjectContext;
import de.rocketlabs.behatide.domain.model.Definition;
import de.rocketlabs.behatide.domain.model.DefinitionContainer;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;

public class DefinitionTreeCell extends TreeCell<Object> {

    public DefinitionTreeCell(ProjectContext projectContext) {
        super();
        setOnMouseClicked(e -> {
            if (getItem() instanceof Definition && e.getClickCount() == 2) {
                EventManager.fireEvent(new DefinitionClickedEvent((Definition) getItem(), projectContext));
            }
        });
    }

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            if (item instanceof DefinitionContainer) {
                setTexts(((DefinitionContainer) item).getName(), null);
            } else if (item instanceof Definition) {
                setTexts(((Definition) item).getMethodName(), ((Definition) item).getDescription());
            } else {
                setTexts(null, null);
            }
        } else {
            setTexts(null, null);
        }
    }

    private void setTexts(String itemText, String toolTipText) {
        setText(itemText);
        if (toolTipText == null || toolTipText.isEmpty()) {
            setTooltip(null);
        } else {
            setTooltip(new Tooltip(toolTipText));
        }
    }
}
