package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private JFXButton dashBtn;

    private AnchorPane parentPane;

    @FXML
    private Label dashboardLbl;


    @FXML
    private AnchorPane menuAnchorPane;


    @FXML
    private AnchorPane menuInnerAnchorPane;

    @FXML
    private GridPane menuGridPane;
    @FXML
    void onDashBtn(ActionEvent event) throws IOException {

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = dashBtn.getScene();

        root.translateYProperty().set(scene.getHeight());
        parentPane.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv= new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event2 ->{
            parentPane.getChildren().removeIf(node -> node != root);
        });
        timeline.play();
    }

    public void initData(AnchorPane parentPane) {
        this.parentPane = parentPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        menuAnchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
//                System.out.println(parentPane.getScene()); // Prints the Scene
//                EventHandler<KeyEvent> keyEventHandler = new MyKeyEventHandler();
//                menuAnchorPane.getScene().setOnKeyPressed(keyEventHandler);

                parentPane.widthProperty().addListener((obs1, oldVal1, newVal1) -> {
                    // Get the height of the AnchorPane
                    double anchorPaneWidth = parentPane.getWidth();

                    // Get the height of the Button
                    double buttonWidth= dashBtn.getWidth();

                    // Calculate the y-coordinate to center the Button vertically
                    double buttonLayoutY = (anchorPaneWidth - buttonWidth) / 2;

                    System.out.println(anchorPaneWidth+" "+buttonWidth);
                    // Set the layoutY property of the Button
                    dashBtn.setLayoutX(buttonLayoutY);

                    double lblWidth= dashboardLbl.getWidth();
                    double lblLayoutX = (anchorPaneWidth - lblWidth) / 2;
                    dashboardLbl.setLayoutX(lblLayoutX);
                });
            }
        });


        menuAnchorPane.parentProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {

                AnchorPane parent = (AnchorPane) menuAnchorPane.getParent();
                    // Update the left and right anchors of myAnchorPane to take up the full width
                    Scene scene = parent.getScene();
                    Window window = scene.getWindow();
                    double windowWidth = window.getWidth();

                    menuAnchorPane.setMinWidth(windowWidth);
                    AnchorPane.setLeftAnchor(menuAnchorPane, 0.0);
                    AnchorPane.setRightAnchor(menuAnchorPane, 0.0);
                    AnchorPane.setTopAnchor(menuAnchorPane, 0.0);
                    AnchorPane.setBottomAnchor(menuAnchorPane, 0.0);
                    menuGridPane.setMinWidth(windowWidth);
                    AnchorPane.setLeftAnchor(menuInnerAnchorPane, 0.0);
                    AnchorPane.setRightAnchor(menuInnerAnchorPane, 0.0);
                    menuInnerAnchorPane.setMinWidth(windowWidth);

            }
        });



        // Listen for changes to the width of the parent container
//        AnchorPane parent = (AnchorPane)menuAnchorPane.getParent();
//        parent.widthProperty().addListener((obs, oldVal, newVal) -> {
//            // Update the left and right anchors of myAnchorPane to take up the full width
//            AnchorPane.setLeftAnchor(menuAnchorPane, 0.0);
//            AnchorPane.setRightAnchor(menuAnchorPane, 0.0);
//        });
    }

    private class MyKeyEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.A) {
                // Handle ESC key press
                System.out.println("A pressed");
            } else {
                System.out.println("Key pressed: " + event.getCode().toString());
            }
        }
    }
    @FXML
    void onItemClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));
        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void openItemForm(){

    }
}
