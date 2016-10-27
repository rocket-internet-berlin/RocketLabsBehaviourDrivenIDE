package de.rocketlabs.behatide.application.component.widget;

import de.rocketlabs.behatide.application.component.FxmlLoading;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class WidgetButton extends ToggleButton implements FxmlLoading {

    @FXML
    private ImageView imageView;
    @FXML
    private Label label;
    private Widget widget;

    WidgetButton() {
        this(Orientation.HORIZONTAL);
    }

    WidgetButton(Orientation orientation) {
        loadFxml();
        if (orientation == Orientation.VERTICAL) {
            getTransforms().add(new Rotate(-90));
            translateYProperty().bind(widthProperty());
        }
    }

    @Override
    public String getFxmlPath() {
        return "/view/widget/WidgetButton.fxml";
    }

    Widget getWidget() {
        return widget;
    }

    void setWidget(Widget widget) {
        this.widget = widget;
    }

    public Image getImage() {
        return imageView.getImage();
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public ObjectProperty<Image> imageProperty() {
        return imageView.imageProperty();
    }
}
