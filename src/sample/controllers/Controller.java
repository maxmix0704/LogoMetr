package sample.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import sample.dao.DAOFactory;
import sample.dao.interfaces.LogoDAO;
import sample.entity.Logo;
import sample.dao.impl.CollectionLogoDAO;
import sample.start.Main;
import sample.utils.EventTypeLogo;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public Button btnSaveImg;
    public Label lblResult;

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
    Boolean isZoomed =false;

    public static GraphicsContext gc;

    float startX;
    float startY;
    float endX;
    float endY;

    public static float logoSize;

    Image defImage;
    Image resImage;
    Rectangle rectangle;


    DAOFactory daoFactory;
    Connection connection;
    LogoDAO dao;

    public static Logo editLogo;

    private static Color COLOR_TEXT_PERCENT_OF_LOGO = Color.rgb(141,19,144, 1);
    private static Color COLOR_TEXT_SELECT_FRAME = Color.rgb(49,38,255,0.9);
    private static Color COLOR_TEXT_SELECT_LOGO = Color.rgb(49,38,255,0.9);
    private static Color COLOR_FRAME_OF_LOGO = Color.rgb(255,117,4,0.8);

    @FXML
    public void btnCreateFrame(ActionEvent event) {
        primaryStage.hide();
        captureController.clearCanvas(captureController.gc);
        captureController.clearCanvas(this.gc);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        captureStage.show();
        captureController.setScreenCapture();
        showText(captureController.gc,
                "%s",
                "Hightline the frame",
                100,
                COLOR_TEXT_SELECT_FRAME,
                (int)Math.round(captureController.gc.getCanvas().getWidth()/ 2),
                (int)Math.round(captureController.gc.getCanvas().getHeight()  / 2));
        captureController.setScreenCapture();
        captureStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    defImage = captureController.grabScreenRegion(captureController.getRectAWT(
                            (int) captureController.startX,
                            (int) captureController.startY,
                            (int) captureController.endX,
                            (int) captureController.endY));
                    imgViewMain.setImage(defImage);
                    imgViewMain.setFitWidth(imgViewMain.getImage().getWidth());
                    imgViewMain.setFitHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setHeight(imgViewMain.getImage().getHeight());
                    canvasMain.setWidth(imgViewMain.getImage().getWidth());
                    captureStage.hide();
                    btnCheck.setDisable(false);
                    btnSave.setDisable(true);
                    btnSaveImg.setDisable(true);
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
        primaryStage.setMaximized(true);
        lblResult.setText("--%");
    }

    public void scrollMouse(ScrollEvent scrollEvent) {
        if (!isClicked) {
            float width = (float) imgViewMain.getFitWidth();
            float height = (float) imgViewMain.getFitHeight();
            float kZoom;
            if (!isZoomed) {
                resImage = getResultImage();
                imgViewMain.setImage(resImage);
                captureController.clearCanvas(this.gc);
                isZoomed = true;
            }
            if (scrollEvent.getDeltaY() > 0) {
                kZoom = 0.9f;
            } else {
                kZoom = 1.1f;
            }
            startX *= kZoom;
            startY *= kZoom;
            endX *= kZoom;
            endY *= kZoom;
            width *= kZoom;
            height *= kZoom;

            imgViewMain.setFitWidth(width);
            imgViewMain.setFitHeight(height);
            canvasMain.setWidth(width);
            canvasMain.setHeight(height);
        }
    }

    public void movedMouseMain(MouseEvent mouseEvent) {
        if  (isClicked&&isPressCheck) {
            isRectCreate =true;
            endX = (int) mouseEvent.getX();
            endY = (int) mouseEvent.getY();
            rectangle=captureController.getRectAWT(startX,startY,endX,endY);
            logoSize=calcLogoSize();
            captureController.redrawRect(this.gc,rectangle);
            showText(this.gc, "%.2f%%", calcLogoSize(),20,COLOR_TEXT_PERCENT_OF_LOGO,(int) startX+50,(int)startY+15);
            lblResult.setText(Float.toString(round(calcLogoSize(),2))+"%");
            lblResult.setFont(Font.font("Verdana",FontWeight.BOLD,15));
        }
    }

    public void pressMouseMain(MouseEvent mouseEvent) {
        if (!isClicked&&isPressCheck) {
            isClicked = true;
            isCalcSize = false;
            isZoomed=false;
            captureController.clearCanvas(this.gc);
            imgViewMain.setImage(defImage);
            this.startX= (int) mouseEvent.getX();
            this.startY= (int) mouseEvent.getY();
            btnSave.setDisable(true);
            btnSaveImg.setDisable(true);
        }
        else {
            isClicked=false;
            canvasMain.requestFocus();
            System.out.println(startX+" "+startY+"-"+endX+" "+endY);
        }
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

        imgViewMain.setImage(new Image("images/not_found.png"));
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
        btnSaveImg.setDisable(true);

        lblResult.setFont(Font.font("Verdana",FontWeight.BOLD,15));
        lblResult.setText("--%");

        accord.setExpandedPane(titledPane1);
        this.logoDao = new CollectionLogoDAO();
    }


    public void pressEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            isCalcSize = true;
            btnSave.setDisable(false);
            btnSaveImg.setDisable(false);
        }
    }

    public void showText(GraphicsContext gc, String format, Object text, int size, Color color, int x, int y) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font ("Verdana",FontWeight.BOLD, size));
        gc.setFill(color);
        StringBuilder builder = new StringBuilder();
        Formatter formatter = new Formatter(builder);
        formatter.format(format,text);
        gc.fillText(builder.toString(),x,y);
    }

    public void btnCheckLogo(ActionEvent event) {
        isPressCheck =true;
        btnCheck.setDisable(true);
        canvasMain.setFocusTraversable(true);
        showText(this.gc,"%s","Hightline the logo", 25,COLOR_TEXT_SELECT_LOGO,(int) canvasMain.getWidth()/2,(int)canvasMain.getHeight()/2);
    }

    public float calcLogoSize(){
        float result;
        float sizeFrame = (float) (canvasMain.getHeight()*canvasMain.getWidth());
        float sizeLogo = rectangle.height*rectangle.width;
        result=(sizeLogo*100)/sizeFrame;
        if (result>0)
            return round(result,2);
        return 0;
    }

    public static float getLogoSize(){
        return logoSize;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public void btnSaveLogo(ActionEvent event){
        dialogStage.setTitle("Save");
        dialogWindowController.productNameField.requestFocus();
        dialogWindowController.btnDialogWindow.setText("Save");
        dialogStage.showAndWait();
    }

    public void updateTable() {
        tableView.setItems(dao.getAll());
    }

    public static Stage getDialogStage(){
        return dialogStage;
    }

    public void btnEditLogo(ActionEvent event) {
        dialogStage.setTitle("Edit");
        dialogWindowController.btnDialogWindow.setText("Edit");
        Logo logo = (Logo) tableView.getSelectionModel().getSelectedItem();
        dialogWindowController.productNameField.setText(logo.getProductName());
        dialogWindowController.idBaseField.setText(Integer.toString(logo.getIdBase()));
        this.editLogo = logo;
        dialogStage.showAndWait();
    }

    public void btnDeleteLogo(ActionEvent event) {
        dao.delete((Logo) tableView.getSelectionModel().getSelectedItem());
        updateTable();
    }

    public void dblClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            isPressCheck=false;
            captureController.clearCanvas(this.gc);
            lblResult.setText("--%");
            Logo logo = (Logo) tableView.getSelectionModel().getSelectedItem();
            resImage=dao.find(logo.getId()).getImage();
            imgViewMain.setImage(resImage);
            System.out.println(logo.getId());
            btnSave.setDisable(true);
            btnSaveImg.setDisable(false);
        }
    }

    public Image getResultImage(){
        Image image = imgViewMain.getImage();
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image snapshot = canvasMain.snapshot(params, null);

        BufferedImage bImage2 = SwingFXUtils.fromFXImage(snapshot, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            ImageIO.write(bImage2, "png", outputStream);
            byte[] res  = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(res);
            snapshot = new Image(inputStream,image.getWidth(),image.getHeight(),false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bImage2 = SwingFXUtils.fromFXImage(snapshot, null);
        float alpha = 1f;
        int compositeRule = AlphaComposite.SRC_OVER;
        AlphaComposite ac;
        int imgW = (int) image.getWidth();
        int imgH = (int) image.getHeight();
        BufferedImage overlay = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = overlay.createGraphics();
        ac = AlphaComposite.getInstance(compositeRule, alpha);
        g.drawImage(bImage,0,0,null);
        g.setComposite(ac);
        g.drawImage(bImage2,0,0,null);
        g.setComposite(ac);
        Image imageRes = SwingFXUtils.toFXImage(overlay, null);
        g.dispose();
        return imageRes;
    }

    public void btnSaveLogoImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(resImage,
                        null), "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void clearRect(){
        captureController.clearCanvas(this.gc);
    }

    public void showImage(ActionEvent event){
        getResultImage();
    }
}
