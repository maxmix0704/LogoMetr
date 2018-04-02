package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainWindow.fxml"));
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
}
