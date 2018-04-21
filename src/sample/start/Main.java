package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.dao.DAOFactory;
import sample.dao.factory.PostgreSQLDAOFactory;
import sample.dao.impl.PostgresLogoDAO;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    private static Stage primaryStage;
    private static Parent root;
    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        loader = new FXMLLoader(getClass().getResource("../fxml/MainWindow.fxml"));
        this.root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("LogoMetr");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinHeight(420);
        primaryStage.setMinWidth(640);
        primaryStage.show();

    }


    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    public static FXMLLoader getLoader(){
        return loader;
    }
}
