package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
    Canvas canvasMain;

    @FXML
    public void pressBtnCreateFrame(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CaptureWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptureController controller = loader.getController();
        stage.setScene(new Scene(root,controller.getWidthScreen(),controller.getHeightScreen()));
        stage.setX(0);
        stage.setY(0);
        controller.canvas.setHeight(controller.getHeightScreen());
        controller.canvas.setWidth(controller.getWidthScreen());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        controller.setImage();
        stage.show();
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    imgViewMain.setImage(controller.grabScreenRegion(controller.startX,controller.startY,controller.endX,controller.endY));
                    imgViewMain.setFitWidth(imgViewMain.getImage().getWidth());
                    imgViewMain.setFitHeight(imgViewMain.getImage().getHeight());
                    stage.close();
                }
            }
        });
    }

    public void scrollMouse(ScrollEvent scrollEvent) {
        int width = (int) imgViewMain.getFitWidth();
        int height = (int) imgViewMain.getFitHeight();
        if (scrollEvent.getDeltaY()>0){
            width*=0.9;
            height*=0.9;
        }
        else
        {
            width*=1.1;
            height*=1.1;
        }
        imgViewMain.setFitWidth(width);
        imgViewMain.setFitHeight(height);
        canvasMain.setWidth(width);
        canvasMain.setHeight(height);
    }

}
