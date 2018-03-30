package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.*;


import java.io.IOException;


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
        CaptureController personController = loader.getController();
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
