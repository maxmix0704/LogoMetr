package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static Parent root;
    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        this.root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("LogoMetr");
        primaryStage.getIcons().add(new Image("images/icon.png"));
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
