package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;


import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    public ImageView imgViewMain;

    @FXML
    public StackPane stackPane;
    public Button btnCheck;
    public Button btnSave;
    public Button btnCreate;
    public TitledPane titledPane1;
    public TitledPane titledPane2;
    public TitledPane titledPane3;
    public Accordion accord;

    @FXML
    Canvas canvasMain;

    Stage stage;

    CaptureController controller;

    Parent root;

    Boolean isClicked=false;
    Boolean isPressCheck =false;
    Boolean isCalcSize = false;
    Boolean isFrameCreate = false;

    GraphicsContext gc;

    double startX;
    double startY;
    double endX;
    double endY;

    double logoSize = 0;
    Color color = Color.rgb(255,157,14,0.8);

    @FXML
    public void pressBtnCreateFrame(ActionEvent event) {
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
                    imgViewMain.setImage(controller.grabScreenRegion((int) controller.startX,(int) controller.startY,(int) controller.endX,(int) controller.endY));
                    imgViewMain.setFitWidth(imgViewMain.getImage().getWidth());
                    imgViewMain.setFitHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setWidth(imgViewMain.getImage().getWidth());
                    stage.hide();
                    btnCheck.setDisable(false);
                }
            }
        });
    }

    public void scrollMouse(ScrollEvent scrollEvent) {
        double width = imgViewMain.getFitWidth();
        double height = imgViewMain.getFitHeight();
        if (scrollEvent.getDeltaY()>0){
            width*=0.9;
            height*=0.9;
            startX*=0.9;
            startY*=0.9;
            endX*=0.9;
            endY*=0.9;
        }
        else
        {
            width*=1.1;
            height*=1.1;
            startX*=1.1;
            startY*=1.1;
            endX*=1.1;
            endY*=1.1;
        }
        imgViewMain.setFitWidth(width);
        imgViewMain.setFitHeight(height);
        canvasMain.setWidth(width);
        canvasMain.setHeight(height);
        if (isPressCheck&&!isClicked){
            controller.clearRect(gc);
            controller.drawRect(this.gc,color, startX, startY, endX-startX, endY-startY);
        }
        if (isCalcSize) showSize(gc,logoSize);
    }

    public void movedMouseMain(MouseEvent mouseEvent) {
        if  (isClicked&&isPressCheck) {
            controller.clearRect(this.gc);
            endX = (int) mouseEvent.getX();
            endY = (int) mouseEvent.getY();
            controller.drawRect(this.gc, color,startX, startY, endX-startX, endY-startY);
            gc.strokeRect(startX,startY,endX-startX,endY-startY);
        }
    }

    public void pressMouseMain(MouseEvent mouseEvent) {
        if (!isClicked&&isPressCheck) {
            isClicked = true;
            isCalcSize = false;
            controller.clearRect(this.gc);
            startX= (int) mouseEvent.getX();
            startY= (int) mouseEvent.getY();
        }
        else {
            isClicked=false;
            canvasMain.requestFocus();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CaptureWindow.fxml"));
        this.root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = loader.getController();
        this.gc=canvasMain.getGraphicsContext2D();
        btnCheck.setDisable(true);
        btnSave.setDisable(true);
        accord.setExpandedPane(titledPane1);
    }


    public void pressEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.printf("%.2f%%",getLogoSize());
            System.out.println();
            System.out.println("Frame="+canvasMain.getHeight()*canvasMain.getWidth());
            System.out.println("Logo="+(endX-startX)*(endY-startY));
            logoSize=getLogoSize();
            isCalcSize = true;
            showSize(this.gc,logoSize);
        }
    }

    public void showSize(GraphicsContext gc, double size) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font ("Verdana", 35));
        gc.setFill(Color.rgb(94, 194,48,0.8));
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format("%.2f%%",size);
        gc.fillText(builder.toString(),
                Math.round(canvasMain.getWidth()  / 2),
                Math.round(canvasMain.getHeight() / 2));
    }

    public void checkLogo(ActionEvent event) {
        isPressCheck =true;
        canvasMain.setFocusTraversable(true);
    }

    public double getLogoSize(){
        double sizeFrame = (canvasMain.getHeight()*canvasMain.getWidth());
        double sizeLogo = (endX-startX)*(endY-startY);
        return (sizeLogo*100)/sizeFrame;
    }
}
