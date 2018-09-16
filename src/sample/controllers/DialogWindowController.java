package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.dao.DAOFactory;
import sample.dao.interfaces.LogoDAO;
import sample.entity.Logo;
import sample.start.Main;
import sample.utils.EventTypeLogo;

import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DialogWindowController implements Initializable {


    Stage stage;

    DAOFactory daoFactory;
    Connection connection;
    LogoDAO dao;
    FXMLLoader loader;
    Controller controller;
    CaptureController captureController;

    @FXML
    public TextField productNameField;
    @FXML
    public Button btnDialogWindow;
    @FXML
    public TextField idBaseField;

    public void show(){
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage=Controller.getDialogStage();
        this.loader=Main.getLoader();
        stage.getIcons().add(new Image("images/icon.png"));
        controller=loader.getController();
    }

    public void saveButton(ActionEvent event) {
        this.dao=controller.getDao();
        Logo editLogo = new Logo();
        DateFormat outputformat = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        Date date = null;
        String output = null;
        if ((!productNameField.getText().isEmpty())&&(!idBaseField.getText().isEmpty())) {
            if (!idBaseField.getText().matches("\\d+")){
                alert.setHeaderText("idBase must contain only numbers");
                alert.setContentText("Please correct the field");
                alert.showAndWait();
                return;
            }
            switch (btnDialogWindow.getText()) {
                case "Save":
                    if (controller.calcLogoSize() > 12)
                        editLogo.setEventTypeLogo(EventTypeLogo.PANE);
                    else
                        editLogo.setEventTypeLogo(EventTypeLogo.LOGO);
                        date=new Date();
                    output = outputformat.format(date);
                    editLogo.setDate(output);
                    editLogo.setProductName(productNameField.getText());
                    editLogo.setIdBase(Integer.parseInt(idBaseField.getText()));
                    editLogo.setSize(controller.getLogoSize());
                    editLogo.setImage(controller.getResultImage());
                    dao.insert(editLogo);
                    break;
                case "Edit":
                    editLogo=Controller.getEditLogo();
                    editLogo.setProductName(productNameField.getText());
                    editLogo.setIdBase(Integer.parseInt(idBaseField.getText()));
                    editLogo.setId(Controller.getEditLogo().getId());
                    dao.update(editLogo);
                    break;
            }
            this.stage.hide();
            idBaseField.setText("");
            productNameField.setText("");
            controller.updateTable();
            controller.setPressCheck(false);
            controller.setCalcSize(false);
        } else {
            alert.setHeaderText("Some fields are not filled");
            alert.setContentText("Please enter information");
            alert.showAndWait();
        }
    }

    public void pressCancel(ActionEvent event) {
        this.stage.hide();
        idBaseField.setText("");
        productNameField.setText("");
    }
}
