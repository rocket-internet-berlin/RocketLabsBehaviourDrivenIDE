package de.rocketlabs.behatide.application.component.widget;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class WidgetButton extends ToggleButton {

    @FXML
    private ImageView imageView;
    @FXML
    private Label label;
    private Widget widget;

    WidgetButton() {
        this(Orientation.HORIZONTAL);
    }

    WidgetButton(Orientation orientation) {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("/view/widget/WidgetButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            if (orientation == Orientation.VERTICAL) {
                getTransforms().add(new Rotate(-90));
                translateYProperty().bind(widthProperty());
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
