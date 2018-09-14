package sample.entity;

import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.utils.EventTypeLogo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class Logo implements Serializable, Comparable<Logo> {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty productName = new SimpleStringProperty("");
    private SimpleIntegerProperty idBase = new SimpleIntegerProperty(0);
    private SimpleFloatProperty size = new SimpleFloatProperty(0);
    private EventTypeLogo eventTypeLogo;
    private SimpleStringProperty date = new SimpleStringProperty("01/01/1970");
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

    public Logo(Logo logo){
        this.id = logo.idProperty();
        this.productName = logo.productNameProperty();
        this.idBase = logo.idBaseProperty();
        this.size = logo.sizeProperty();
        this.eventTypeLogo = logo.getEventTypeLogo();
        this.date = logo.dateProperty();
        this.image = logo.getImage();
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

    public float getSize() {
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(getId());
        s.writeInt(getIdBase());
        s.writeUTF(getProductName());
        s.writeFloat(getSize());
        s.writeObject(getEventTypeLogo());
        s.writeUTF(getDate());
        byte[] res = null;
        BufferedImage bImage2 = SwingFXUtils.fromFXImage(getImage(), null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage2, "png", outputStream);
            res  = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        s.write(res);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        id = new SimpleIntegerProperty(s.readInt());
        idBase = new SimpleIntegerProperty(s.readInt());
        productName = new SimpleStringProperty(s.readUTF());
        size = new SimpleFloatProperty(s.readFloat());
        eventTypeLogo = (EventTypeLogo) s.readObject();
        date = new SimpleStringProperty(s.readUTF());
        image = SwingFXUtils.toFXImage(ImageIO.read(s),null);
    }

    @Override
    public int compareTo(Logo o) {
        if (o.getId()>this.id.get()) return -1;
        if (o.getId()<this.id.get()) return 1;
        else return 0;
    }
}
