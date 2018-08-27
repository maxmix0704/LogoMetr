package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import sample.dao.DAOFactory;
import sample.dao.interfaces.LogoDAO;
import sample.entity.Logo;
import sample.dao.impl.CollectionLogoDAO;
import sample.start.Main;
import sample.utils.EventTypeLogo;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.Formatter;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    private static String TEST_DATA_SQL = "INSERT INTO public.logo(" +
            "\tnameproduct, idbase, sizelogo, type_event, date)" +
            "\tVALUES ('Rozetka', 2194, 33.5, 'PANE', '04/03/18');" +
            "\t\n" +
            "INSERT INTO public.logo(" +
            "\tnameproduct, idbase, sizelogo, type_event, date)" +
            "\tVALUES ('Coca Cola', 2195, 10.2, 'LOGO', '05/03/18');" +
            "\t\n" +
            "INSERT INTO public.logo(" +
            "\tnameproduct, idbase, sizelogo, type_event, date)" +
            "\tVALUES ('Onkyo', 2193, 1.2, 'LOGO', '05/03/18'" +
            "\n" +
            "INSERT INTO public.logo(\n" +
            "\tnameproduct, idbase, sizelogo, type_event, date)\n" +
            "\tVALUES ('BMW', 4215, 15, 'PANE', '15/03/18');" +
            "\t\n" +
            "INSERT INTO public.logo(\n" +
            "\tnameproduct, idbase, sizelogo, type_event, date)" +
            "\tVALUES ('Microsof', 1252, 17, 'PANE', '01/03/18');";

    @FXML
    public ImageView imgViewMain;

    @FXML
    public StackPane stackPane;
    public Button btnCheck;
    public Button btnSave;
    public Button btnCreate;
    public TitledPane titledPane1;
    public TitledPane titledPane2;
    public TitledPane titledPane3;
    public Accordion accord;

    public TableView tableView;
    public TableColumn<Logo,Integer> id;
    public TableColumn<Logo,String> productName;
    public TableColumn<Logo,Integer> idBase;
    public TableColumn<Logo,Double> size;
    public TableColumn<Logo,String> date;
    public TableColumn<Logo,EventTypeLogo> eventTypeLogo;
    public Button show;

    @FXML
    Canvas canvasMain;

    Stage captureStage;
    Stage primaryStage;
    static Stage dialogStage;


    CaptureController captureController;
    DialogWindowController dialogWindowController;
    Controller controller;

    CollectionLogoDAO logoDao;

    Parent root;

    Boolean isClicked=false;
    Boolean isPressCheck =false;
    Boolean isCalcSize = false;
    Boolean isFrameCreate = false;
    Boolean isRectCreate = false;

    public static GraphicsContext gc;

    float startX;
    float startY;
    float endX;
    float endY;

    private static float logoSize = 0;

    DAOFactory daoFactory;
    Connection connection;
    LogoDAO dao;

    public static Logo editLogo;

    private static Color COLOR_TEXT_PERCENT_OF_LOGO = Color.rgb(141,19,144, 1);
    private static Color COLOR_TEXT_SELECT_FRAME = Color.rgb(49,38,255,0.9);
    private static Color COLOR_TEXT_SELECT_LOGO = Color.rgb(49,38,255,0.9);
    private static Color COLOR_FRAME_OF_LOGO = Color.rgb(255,117,4,0.8);

    @FXML
    public void pressBtnCreateFrame(ActionEvent event) {
        primaryStage.hide();
        captureController.clearRect(captureController.gc);
        captureController.clearRect(this.gc);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        captureStage.show();
        captureController.setScreenCapture();
        showText(captureController.gc,"%s","Hightline the frame",100, COLOR_TEXT_SELECT_FRAME);
        captureController.setScreenCapture();
        captureStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    imgViewMain.setImage(captureController.grabScreenRegion((int) captureController.startX,(int) captureController.startY,(int) captureController.endX,(int) captureController.endY));
                    imgViewMain.setFitWidth(imgViewMain.getImage().getWidth());
                    imgViewMain.setFitHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setWidth(imgViewMain.getImage().getWidth());
                    captureStage.hide();
                    btnCheck.setDisable(false);
                    primaryStage.show();
                }
                if (keyEvent.getCode() == KeyCode.ESCAPE){
                    captureStage.hide();
                    primaryStage.show();
                }
            }
        });
        isPressCheck=false;
        isCalcSize=false;
    }

    public void scrollMouse(ScrollEvent scrollEvent) {
        float width = (float)imgViewMain.getFitWidth();
        float height = (float)imgViewMain.getFitHeight();
        if (scrollEvent.getDeltaY()>0){
            width*=0.9;
            height*=0.9;
            startX*=0.9;
            startY*=0.9;
            endX*=0.9;
            endY*=0.9;
        }
        else
        {
            width*=1.1;
            height*=1.1;
            startX*=1.1;
            startY*=1.1;
            endX*=1.1;
            endY*=1.1;
        }
        imgViewMain.setFitWidth(width);
        imgViewMain.setFitHeight(height);
        canvasMain.setWidth(width);
        canvasMain.setHeight(height);
            if (isPressCheck && !isClicked) {
                captureController.redrawRect(this.gc, COLOR_FRAME_OF_LOGO, startX, startY, endX - startX, endY - startY);
                if (isCalcSize) showText(gc, "%.2f%%", logoSize, 35, COLOR_TEXT_PERCENT_OF_LOGO);
            }
    }

    public void movedMouseMain(MouseEvent mouseEvent) {
        if  (isClicked&&isPressCheck) {
            isRectCreate =true;
            endX = (int) mouseEvent.getX();
            endY = (int) mouseEvent.getY();
            captureController.redrawRect(this.gc,COLOR_FRAME_OF_LOGO,startX, startY, endX-startX, endY-startY);
            gc.strokeRect(startX,startY,endX-startX,endY-startY);
        }
    }

    public void pressMouseMain(MouseEvent mouseEvent) {
        if (!isClicked&&isPressCheck) {
            isClicked = true;
            isCalcSize = false;
            captureController.clearRect(this.gc);
            startX= (int) mouseEvent.getX();
            startY= (int) mouseEvent.getY();
        }
        else {
            isClicked=false;
            canvasMain.requestFocus();
        }
        btnSave.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.daoFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        this.connection = daoFactory.createConnection();
        this.dao = daoFactory.getLogoDAO();
        updateTable();

        id.setCellValueFactory(new PropertyValueFactory<Logo, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Logo, String>("productName"));
        idBase.setCellValueFactory(new PropertyValueFactory<Logo, Integer>("idBase"));
        date.setCellValueFactory(new PropertyValueFactory<Logo, String>("date"));
        size.setCellValueFactory(new PropertyValueFactory<Logo, Double>("size"));
        eventTypeLogo.setCellValueFactory(new PropertyValueFactory<Logo, EventTypeLogo>("eventTypeLogo"));
        this.primaryStage = Main.getPrimaryStage();

        this.captureStage = new Stage();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../fxml/CaptureWindow.fxml"));
        this.root = null;
        try {
            root = loader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.captureController = loader1.getController();

        this.dialogStage = new Stage();
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../fxml/DialogWindow.fxml"));
        this.root = null;
        try {
            root = loader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dialogWindowController = loader2.getController();
        dialogStage.setScene(new Scene(loader2.getRoot()));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setResizable(false);
        dialogStage.initOwner(primaryStage);

        captureStage.setScene(new Scene(loader1.getRoot(), captureController.getWidthScreen(), captureController.getHeightScreen()));
        captureStage.setX(0);
        captureStage.setY(0);
        captureController.canvas.setHeight(captureController.getHeightScreen());
        captureController.canvas.setWidth(captureController.getWidthScreen());
        captureStage.initModality(Modality.WINDOW_MODAL);
        captureStage.initOwner(primaryStage);
        captureStage.initStyle(StageStyle.UNDECORATED);
        this.gc=canvasMain.getGraphicsContext2D();
        btnCheck.setDisable(true);
        btnSave.setDisable(true);
        accord.setExpandedPane(titledPane1);
        this.logoDao = new CollectionLogoDAO();
    }


    public void pressEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println(getLogoSize());
            System.out.println();
            System.out.println("Frame="+canvasMain.getHeight()*canvasMain.getWidth());
            System.out.println("Logo="+(endX-startX)*(endY-startY));
            logoSize=getLogoSize();
            isCalcSize = true;
            showText(this.gc,"%.2f%%",logoSize,35,COLOR_TEXT_PERCENT_OF_LOGO);
            btnSave.setDisable(false);
        }
    }

    public void showText(GraphicsContext gc, String format, Object text, int size, Color color) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font ("Verdana", size));
        gc.setFill(color);
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format(format,text);
        gc.fillText(builder.toString(),
                Math.round(gc.getCanvas().getWidth()  / 2),
                Math.round(gc.getCanvas().getHeight() / 2));
    }

    public void checkLogo(ActionEvent event) {
        isPressCheck =true;
        btnCheck.setDisable(true);
        canvasMain.setFocusTraversable(true);
        showText(this.gc,"%s","Hightline the logo", 25,COLOR_TEXT_SELECT_LOGO);
    }

    public float getLogoSize(){
        float sizeFrame = (float) (canvasMain.getHeight()*canvasMain.getWidth());
        float sizeLogo = (endX-startX)*(endY-startY);
        return (sizeLogo*100)/sizeFrame;
    }

    public static float getSize(){
        return round(logoSize,2);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

//    public void saveLogo(ActionEvent event) {
//        EventTypeLogo eventTypeLogo = EventTypeLogo.LOGO;
//        if (logoSize>12) eventTypeLogo = EventTypeLogo.PANE;
//        Logo logo = new Logo();
//        logo.setId(1);
//        logo.setProductName("Rozetka");
//        logo.setIdBase(1444);
//        logo.setSize(logoSize);
//        logo.setEventTypeLogo(eventTypeLogo);
//        logo.setImage(imgViewMain.getImage());
//        logo.setDate(new Date().toString());
//        logoDao.insert(logo);
//        updateTable();
//    }

    public void saveLogo(ActionEvent event){
        dialogStage.setTitle("Save");
        dialogWindowController.btnDialogWindow.setText("Save");
        dialogStage.showAndWait();
    }

    public void updateTable() {
        tableView.setItems(dao.getAll());
    }

    public static Stage getDialogStage(){
        return dialogStage;
    }

    public void editLogo(ActionEvent event) {
        dialogStage.setTitle("Edit");
        dialogWindowController.btnDialogWindow.setText("Edit");
        Logo logo = (Logo) tableView.getSelectionModel().getSelectedItem();
        dialogWindowController.productNameField.setText(logo.getProductName());
        dialogWindowController.idBaseField.setText(Integer.toString(logo.getIdBase()));
        this.editLogo = logo;
        dialogStage.showAndWait();
    }

    public void deleteLogo(ActionEvent event) {
        dao.delete((Logo) tableView.getSelectionModel().getSelectedItem());
        updateTable();
    }

    public void showImage(ActionEvent event){
        imgViewMain.setImage(dao.find(1).getImage());
    }

    public void dblClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            isPressCheck=false;
            captureController.clearRect(gc);
            Logo logo = (Logo) tableView.getSelectionModel().getSelectedItem();
            imgViewMain.setImage(dao.find(logo.getId()).getImage());
            System.out.println(logo.getId());
        }
    }

    public void clearRect(){
        captureController.clearRect(this.gc);
    }
}
