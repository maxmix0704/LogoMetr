package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.*;


import java.io.IOException;


public class Controller {

    @FXML
    public ImageView imgViewMain;

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
        personController.canvas.setHeight(personController.getHeightScreen());
        personController.canvas.setWidth(personController.getWidthScreen());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        personController.setImage();
//        personController.setFigure();
        stage.show();
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    imgViewMain.setImage(personController.grabScreenRegion(personController.startX,personController.startY,personController.endX,personController.endY));
                    imgViewMain.setFitWidth(imgViewMain.getImage().getWidth());
                    imgViewMain.setFitHeight(imgViewMain.getImage().getHeight());
                    stage.close();
                }
            }
        });
    }

    public void scrollMouse(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY()>0){
            imgViewMain.setFitWidth(imgViewMain.getFitWidth()*0.9);
            imgViewMain.setFitHeight(imgViewMain.getFitHeight()*0.9);
        }
        else
        {
            imgViewMain.setFitWidth(imgViewMain.getFitWidth()*1.1);
            imgViewMain.setFitHeight(imgViewMain.getFitHeight()*1.1);
        }
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
