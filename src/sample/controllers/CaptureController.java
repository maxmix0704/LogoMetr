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

    float startX;
    float startY;
    float endX;
    float endY;

    public GraphicsContext gc;

    private boolean isClicked = false;

    public void setScreenCapture(){
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

    public void clearCanvas(GraphicsContext gc){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }

    public void fillCanvas(GraphicsContext gc) {
        gc.setFill(Color.rgb(149,249,78,0.2));
        gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }

    public void redrawRect(GraphicsContext gc, Rectangle rectangle){
        clearCanvas(gc);
        fillCanvas(gc);
        gc.clearRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
        gc.setStroke(Color.RED);
        gc.strokeRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    public int getHeightScreen(){
        return (int) Screen.getPrimary().getBounds().getHeight();
    }

    public int getWidthScreen(){
        return (int) Screen.getPrimary().getBounds().getWidth();
    }

    public void moveMouse(MouseEvent mouseEvent) {
        if  (isClicked) {
            this.endX = MouseInfo.getPointerInfo().getLocation().x;
            this.endY = MouseInfo.getPointerInfo().getLocation().y;
            redrawRect(this.gc,getRectAWT(startX,startY,endX,endY));
        }
    }

    public void pressMouseButton(MouseEvent mouseEvent) {
        if (!isClicked) {
            isClicked = true;
            clearCanvas(this.gc);
            startX=MouseInfo.getPointerInfo().getLocation().x;
            startY=MouseInfo.getPointerInfo().getLocation().y;
        }
        else {
            isClicked=false;
        }
    }

    public Rectangle getRectAWT(float x, float y, float x2, float y2){
        float startX;
        float startY;
        float width;
        float height;
        if ((x2<x)&&(y2<y)){
            startX=x2;
            startY=y2;
            width=x-x2;
            height=y-y2;
        } else
            if ((x2>x)&&(y2<y)){
                startX=x;
                startY=y-(y-y2);
                width=x2-x;
                height=y-y2;
            } else
                if ((x2<x)&&(y2>y)){
                    startX=x2;
                    startY=y2-(y2-y);
                    width=x-x2;
                    height=y2-y;
                    } else
                        {
                        startX=x;
                        startY=y;
                        width=x2-x;
                        height=y2-y;
                        }
        return new Rectangle((int) startX,(int) startY,(int) width,(int) height);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = canvas.getGraphicsContext2D();
    }

    public void setStage(Stage stage){
       this.captureStage = stage;
    }
}
