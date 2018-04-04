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
import sample.utils.EventTypeLogo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.ResourceBundle;

public class DialogWindowController implements Initializable {


    Controller controller;
    Stage stage;
    Parent root;

    DAOFactory daoFactory;
    Connection connection;
    LogoDAO dao;

    @FXML
    public TextField productNameField;
    @FXML
    public TextField idBaseField;

    public void show(){
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.stage = new Stage();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CaptureWindow.fxml"));
//        this.root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.controller = loader.getController();
//        stage.setScene(new Scene(root));

        this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.connection = daoFactory.createConnection();
        this.dao = daoFactory.getLogoDAO();
        this.stage=Controller.getDialogStage();
    }

    public void saveButton(ActionEvent event) {
        Logo logo = new Logo();
        if (Controller.getSize()>12)
            logo.setEventTypeLogo(EventTypeLogo.PANE);
        else
            logo.setEventTypeLogo(EventTypeLogo.LOGO);
        logo.setIdBase(Integer.parseInt(idBaseField.getText()));
        logo.setDate(new Date().toString());
        logo.setProductName(productNameField.getText());
        logo.setSize(Controller.getSize());
        dao.insert(logo);
        this.stage.hide();
        idBaseField.setText("");
        productNameField.setText("");
    }
}
