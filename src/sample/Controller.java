package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Controller {
    @FXML
    Label label1;

    public void pressButton(ActionEvent event){
        label1.setText("Hello");
    }
}
