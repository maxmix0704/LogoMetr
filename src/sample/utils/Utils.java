package sample.utils;

import com.sun.javafx.scene.control.Logging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Logo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void saveDb(ObservableList<Logo> list) {

        try {
            FileOutputStream fos = new FileOutputStream("collectionDB.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<Logo>(list));
            oos.close();
        } catch (NotSerializableException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
           exception.printStackTrace();
        }
    }

    public static ObservableList<Logo> loadDb() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        List<Logo> logo = null;
        try {
            FileInputStream fis = new FileInputStream("collectionDB.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            logo = (List<Logo>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(logo);
    }

}