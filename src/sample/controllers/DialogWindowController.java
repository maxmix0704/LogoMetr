package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dao.DAOFactory;
import sample.dao.interfaces.LogoDAO;
import sample.entity.Logo;
import sample.start.Main;
import sample.utils.EventTypeLogo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
        this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.connection = daoFactory.createConnection();
        this.dao = daoFactory.getLogoDAO();
        this.stage=Controller.getDialogStage();
        this.loader=Main.getLoader();
        controller=loader.getController();
    }

    public void saveButton(ActionEvent event) {
        Logo editLogo = new Logo();
        switch (btnDialogWindow.getText()){
            case "Save":
                if (Controller.getSize()>12)
                    editLogo.setEventTypeLogo(EventTypeLogo.PANE);
                else
                    editLogo.setEventTypeLogo(EventTypeLogo.LOGO);
                editLogo.setDate(new Date().toString());
                editLogo.setProductName(productNameField.getText());
                editLogo.setIdBase(Integer.parseInt(idBaseField.getText()));
                editLogo.setSize(Controller.getSize());
//                editLogo.setImage(controller.imgViewMain.getImage());
                editLogo.setImage(controller.getResultImage());
                dao.insert(editLogo);
                break;
            case "Edit":
                editLogo.setProductName(productNameField.getText());
                editLogo.setIdBase(Integer.parseInt(idBaseField.getText()));
                editLogo.setId(Controller.editLogo.getId());
                dao.update(editLogo);
                break;
        }
        this.stage.hide();
        idBaseField.setText("");
        productNameField.setText("");
        controller.updateTable();
        controller.clearRect();
        controller.isPressCheck=false;
        controller.isCalcSize=false;

    }

    public void pressCancel(ActionEvent event) {
        this.stage.hide();
        idBaseField.setText("");
        productNameField.setText("");
    }
}
