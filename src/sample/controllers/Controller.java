package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.stage.Window;
import sample.start.Main;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    Button btn1;

    @FXML
    public void pressBtn(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CaptureWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        СaptureController personController = loader.getController();
        stage.setScene(new Scene(root,personController.getWidthScreen(),personController.getHeightScreen()));
        stage.setX(0);
        stage.setY(0);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        personController.setImage();
        stage.show();
    }



//    public void initialize() {
//        File file = new File("D:/2.gif");
//        String localUrl = null;
//        try {
//            localUrl = file.toURI().toURL().toString();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Image i = new Image(localUrl);
//        imageFX.setImage(i);
//    }

}
