package de.rocketlabs.behatide.application.component.widget;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.DefaultProperty;
import javafx.beans.binding.When;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@DefaultProperty("widgets")
public class WidgetToolBar extends ToolBar {

    @FXML
    private ObjectProperty<Pane> contentPane = new SimpleObjectProperty<>();
    @FXML
    private ObservableList<Widget> widgets = new ObservableListWrapper<>(new LinkedList<>());

    private ObjectProperty<SplitPane> splitPane = new SimpleObjectProperty<>();
    private IntegerProperty contentPosition = new SimpleIntegerProperty();

    private Map<Widget, Group> widgetButtons = new HashMap<>();
    private ToggleGroup buttonGroup = new ToggleGroup();
    private Node currentContent;
    private float deviderPosition;

    public WidgetToolBar() {
        super();

        widgets.addListener(this::onWidgetChanged);
        orientationProperty().addListener(this::orientationChanged);
        buttonGroup.selectedToggleProperty().addListener(this::updateContent);
    }

    private void addWidget(Widget widget) {
        Group widgetButton = createWidgetButton(widget, orientationProperty().get());
        widgetButtons.put(widget, widgetButton);
        getItems().add(widgetButton);
    }

    private Group createWidgetButton(Widget widget, Orientation orientation) {
        WidgetButton button = new WidgetButton(orientation);
        button.imageProperty().bind(widget.imageProperty());

        button.textProperty().bind(
                new When(widget.mnemonicProperty().isNotNull())
                        .then(new SimpleStringProperty("_")
                                      .concat(widget.mnemonicProperty())
                                      .concat(": ")
                                      .concat(widget.nameProperty()))
                        .otherwise(widget.nameProperty())
        );
        button.setToggleGroup(buttonGroup);
        button.setWidget(widget);

        return new Group(button);
    }

    private void onWidgetChanged(ListChangeListener.Change<? extends Widget> change) {

        while (change.next()) {
            change.getAddedSubList().forEach(this::addWidget);
            change.getRemoved().forEach(this::removeWidget);
        }
    }

    private void orientationChanged(ObservableValue<? extends Orientation> observable,
                                    Orientation oldValue,
                                    Orientation newValue) {
        widgetButtons.forEach((widget, button) -> {
            getItems().remove(button);
            button = createWidgetButton(widget, newValue);
            getItems().add(button);
            widgetButtons.put(widget, button);
        });
    }

    private void removeWidget(Widget widget) {
        getItems().remove(widgetButtons.remove(widget));
    }

    private void updateContent(ObservableValue<? extends Toggle> observableValue, Toggle oldButton, Toggle newButton) {
        ObservableList<Node> items = getSplitPane().getItems();
        items.remove(currentContent);
        if (newButton instanceof WidgetButton) {
            currentContent = ((WidgetButton) newButton).getWidget().getContent();
            items.add(getContentPosition(), currentContent);
        }
    }

    @FXML
    public ObservableList<Widget> getWidgets() {
        return widgets;
    }

    @FXML
    public ObjectProperty<Pane> contentPaneProperty() {
        return contentPane;
    }

    @FXML
    public Pane getContentPane() {
        return contentPane.get();
    }

    @FXML
    public void setContentPane(Pane contentPane) {
        this.contentPane.set(contentPane);
    }

    @FXML
    public SplitPane getSplitPane() {
        return splitPane.get();
    }

    @FXML
    public ObjectProperty<SplitPane> splitPaneProperty() {
        return splitPane;
    }

    @FXML
    public void setSplitPane(SplitPane splitPane) {
        this.splitPane.set(splitPane);
    }

    @FXML
    public int getContentPosition() {
        return contentPosition.get();
    }

    @FXML
    public IntegerProperty contentPositionProperty() {
        return contentPosition;
    }

    @FXML
    public void setContentPosition(int contentPosition) {
        this.contentPosition.set(contentPosition);
    }
}
