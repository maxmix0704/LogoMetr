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
        imgView.setImage(grabScreenRegion(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
    }
    public javafx.scene.image.Image grabScreenRegion(Rectangle rectangle) {
        try {
            return SwingFXUtils.toFXImage(new Robot().createScreenCapture(rectangle), null);
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

    public void drawRect(GraphicsContext gc, int x, int y, int x2, int y2){
        gc.strokeRect(x,y,x2,y2);
        gc.setStroke(color);
        gc.setLineWidth(1);
    }

    public int getHeightScreen(){
        return (int) Screen.getPrimary().getBounds().getHeight();
    }

    public int getWidthScreen(){
        return (int) Screen.getPrimary().getBounds().getWidth();
    }

    public void clearRect(GraphicsContext gc){
        gc.clearRect(0,0,getWidthScreen(),getHeightScreen());
    }

    public void moveMouse(MouseEvent mouseEvent) {
        if  (isClicked) {
            clearRect(this.gc);
            endX = MouseInfo.getPointerInfo().getLocation().x;
            endY = MouseInfo.getPointerInfo().getLocation().y;
            drawRect(this.gc, startX, startY, endX-startX, endY-startY);
        }
    }

    public void pressMouseButton(MouseEvent mouseEvent) {
        if (!isClicked) {
            isClicked = true;
            clearRect(this.gc);
            startX=MouseInfo.getPointerInfo().getLocation().x;
            startY=MouseInfo.getPointerInfo().getLocation().y;
        }
        else {
            isClicked=false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = canvas.getGraphicsContext2D();
    }

    public void setStage(Stage stage){
       this.captureStage = stage;
    }
}
