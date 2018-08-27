package sample.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import sample.utils.EventTypeLogo;

public class Logo {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty productName = new SimpleStringProperty("");
    private SimpleIntegerProperty idBase = new SimpleIntegerProperty(0);
    private SimpleFloatProperty size = new SimpleFloatProperty(0);
    private EventTypeLogo eventTypeLogo;
    private SimpleStringProperty date = new SimpleStringProperty("1/01/2018");

    private Image image;

    public Logo(){}

    public Logo(SimpleIntegerProperty id, SimpleStringProperty productName, SimpleIntegerProperty idDatabase, SimpleFloatProperty sizeLogo, EventTypeLogo eventTypeLogo, SimpleStringProperty date) {
        this.id = id;
        this.productName = productName;
        this.idBase = idDatabase;
        this.size = sizeLogo;
        this.eventTypeLogo = eventTypeLogo;
        this.date = date;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getIdBase() {
        return idBase.get();
    }

    public SimpleIntegerProperty idBaseProperty() {
        return idBase;
    }

    public void setIdBase(int idBase) {
        this.idBase.set(idBase);
    }

    public double getSize() {
        return size.get();
    }

    public SimpleFloatProperty sizeProperty() {
        return size;
    }

    public void setSize(float size) {
        this.size.set(size);
    }


    public EventTypeLogo getEventTypeLogo() {
        return eventTypeLogo;
    }

    public void setEventTypeLogo(EventTypeLogo eventTypeLogo) {
        this.eventTypeLogo = eventTypeLogo;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Logo{" +
                "id=" + id +
                ", productName=" + productName +
                ", idBase=" + idBase +
                ", size=" + size +
                ", eventTypeLogo=" + eventTypeLogo +
                ", date=" + date +
                '}';
    }
}
