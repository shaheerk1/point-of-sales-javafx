package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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


    public void setWidth(double windowWidth) {
//        System.out.println(windowWidth);
//        menuAnchorPane.setPrefWidth(windowWidth);
//        AnchorPane.setLeftAnchor(menuAnchorPane, 0.0);
//        AnchorPane.setRightAnchor(menuAnchorPane, 0.0);
//        menuGridPane.setPrefWidth(windowWidth);
//        AnchorPane.setLeftAnchor(menuInnerAnchorPane, 0.0);
//        AnchorPane.setRightAnchor(menuInnerAnchorPane, 0.0);
//        menuInnerAnchorPane.setPrefWidth(windowWidth);

    }
}
