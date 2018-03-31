package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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

    private int startX;
    private int startY;

    public GraphicsContext gc;

    private boolean isClicked = false;

    public void setImage(){
        imgView.setFitHeight(getHeightScreen());
        imgView.setFitWidth(getWidthScreen());
        imgView.setImage(grabScreen());
    }
    private javafx.scene.image.Image grabScreen() {
        try {
            return SwingFXUtils.toFXImage(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), null);
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
        gc.fillRect(x,y,x2,y2);
        // draw the actual rectangle
        gc.setStroke(Color.RED);
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
            drawRect(startX, startY, MouseInfo.getPointerInfo().getLocation().x-startX, MouseInfo.getPointerInfo().getLocation().y-startY);
        }
    }

    public void getStartMousePosition(MouseEvent mouseEvent) {
        System.out.println("ST"+MouseInfo.getPointerInfo().getLocation().toString());
        isClicked = true;
        clearRect();
        startX=MouseInfo.getPointerInfo().getLocation().x;
        startY=MouseInfo.getPointerInfo().getLocation().y;
    }

    public void getReleasedMousePosition(MouseEvent mouseEvent) {
        System.out.println("RL"+MouseInfo.getPointerInfo().getLocation().toString());
//        isClicked = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gc = canvas.getGraphicsContext2D();
    }
}
