package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CaptureController implements Initializable{

    @FXML
    Label lbl1;

    @FXML
    ImageView imgView;

    @FXML
    Canvas canvas;

    Stage captureStage;

     int startX;
     int startY;
     int endX;
     int endY;

    public GraphicsContext gc;

    private boolean isClicked = false;

    Color color = Color.RED;

    public void setImage(){
        imgView.setFitHeight(getHeightScreen());
        imgView.setFitWidth(getWidthScreen());
        imgView.setImage(grabScreen());
    }
    public javafx.scene.image.Image grabScreen() {
        try {
            return SwingFXUtils.toFXImage(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), null);
        } catch (SecurityException e) {
        } catch (AWTException e) {
        }
        return null;
    }

    public javafx.scene.image.Image grabScreenRegion(int x1, int y1, int x2, int y2) {
        try {

            return SwingFXUtils.toFXImage(new Robot().createScreenCapture(new Rectangle(x1,y1,x2-x1,y2-y1)), null);
        } catch (SecurityException e) {
        } catch (AWTException e) {
        }
        return null;
    }

    public void setFigure(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.fillRect(30,30,100,100);
//        gc.setFill(Color.RED);
            gc.beginPath();
            gc.moveTo(50, 50);
            gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
            gc.closePath();
    }
    public void drawRect(int x, int y, int x2, int y2){
//        this.gc = canvas.getGraphicsContext2D();
        gc.strokeRect(x,y,x2,y2);
        // draw the actual rectangle
        gc.setStroke(color);
        // gc.setFill(model.background)
        gc.setLineWidth(1);
    }

    public int getHeightScreen(){
        return (int) Screen.getPrimary().getBounds().getHeight();
    }

    public int getWidthScreen(){
        return (int) Screen.getPrimary().getBounds().getWidth();
    }

    public void clearRect(){
        gc.clearRect(0,0,getWidthScreen(),getHeightScreen());
    }

    public void getMovedMousePosition(MouseEvent mouseEvent) {
        System.out.println("MV"+MouseInfo.getPointerInfo().getLocation().toString());
        if  (isClicked) {
            clearRect();
            endX = MouseInfo.getPointerInfo().getLocation().x;
            endY = MouseInfo.getPointerInfo().getLocation().y;
            drawRect(startX, startY, endX-startX, endY-startY);
        }
    }

    public void getStartMousePosition(MouseEvent mouseEvent) {
        System.out.println("ST"+MouseInfo.getPointerInfo().getLocation().toString());
        if (!isClicked) {
            isClicked = true;
            clearRect();
            startX=MouseInfo.getPointerInfo().getLocation().x;
            startY=MouseInfo.getPointerInfo().getLocation().y;
        }
        else {
            isClicked=false;
        }
    }

    public void getReleasedMousePosition(MouseEvent mouseEvent) {
        System.out.println("RL"+MouseInfo.getPointerInfo().getLocation().toString());
//        isClicked = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = canvas.getGraphicsContext2D();
    }

    public void pressEnter(KeyEvent keyEvent) {

    }

    public void setStage(Stage stage){
       this.captureStage = stage;
    }
}
