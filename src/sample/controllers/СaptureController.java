package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class СaptureController{

    @FXML
    Label lbl1;

    @FXML
    ImageView imgView;

    public void changeLabel(){
        lbl1.setText("blabla");
    }

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

    public int getHeightScreen(){
        return (int) Screen.getPrimary().getBounds().getHeight();
    }

    public int getWidthScreen(){
        return (int) Screen.getPrimary().getBounds().getWidth();
    }
}
