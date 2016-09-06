package de.rocketlabs.behatide.application.component.widget;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;

@DefaultProperty("content")
public class Widget extends Node {

    protected StringProperty name = new SimpleStringProperty();
    protected ObjectProperty<Character> mnemonic = new SimpleObjectProperty<>();
    protected ObjectProperty<Image> image = new SimpleObjectProperty<>();
    protected ObjectProperty<Node> content = new SimpleObjectProperty<>();

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    public Character getMnemonic() {
        return mnemonic.get();
    }

    public ObjectProperty<Character> mnemonicProperty() {
        return mnemonic;
    }

    public void setMnemonic(Character mnemonic) {
        this.mnemonic.set(mnemonic);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    @Deprecated
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    @Deprecated
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    @Deprecated
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    @Deprecated
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}
