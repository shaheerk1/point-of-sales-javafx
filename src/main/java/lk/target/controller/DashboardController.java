package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane quickBtn1;

    @FXML
    private AnchorPane quickBtn2;

    @FXML
    private AnchorPane quickBtn3;

    @FXML
    private AnchorPane quickBtn4;

    @FXML
    private JFXCheckBox cb1;

    @FXML
    private JFXCheckBox cb2;

    @FXML
    private JFXCheckBox cb3;

    @FXML
    private JFXCheckBox cb4;

    @FXML
    private JFXCheckBox cb5;

    @FXML
    private JFXCheckBox cb6;

    @FXML
    private AnchorPane quickSlectionPane;
    @FXML
    private AnchorPane parentPane;

    private MenuController menuctl;


    @FXML
    private JFXButton menuBtn;
    List<AnchorPane> quickOptions= new ArrayList<>();
    List<Node> quickNodes= new ArrayList<>();

    private ArrayList<String> selectedFavs= new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        parentPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
//                System.out.println(parentPane.getScene()); // Prints the Scene
                EventHandler<KeyEvent> keyEventHandler = new MyKeyEventHandler();
                parentPane.getScene().setOnKeyPressed(keyEventHandler);

                parentPane.widthProperty().addListener((obs1, oldVal1, newVal1) -> {
                    // Get the height of the AnchorPane
                    double anchorPaneWidth = parentPane.getWidth();

                    // Get the height of the Button
                    double buttonWidth= menuBtn.getWidth();

                    // Calculate the y-coordinate to center the Button vertically
                    double buttonLayoutX = (anchorPaneWidth - buttonWidth) / 2;

                    // Set the layoutY property of the Button
                    menuBtn.setLayoutX(buttonLayoutX);

                });
            }
        });





        parentPane.parentProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {

                AnchorPane parent = (AnchorPane) parentPane.getParent();
                // Update the left and right anchors of myAnchorPane to take up the full width
                Scene scene = parent.getScene();
                Window window = scene.getWindow();
                double windowWidth = window.getWidth();




                parentPane.setMinWidth(windowWidth);
                AnchorPane.setLeftAnchor(parentPane, 0.0);
                AnchorPane.setRightAnchor(parentPane, 0.0);
                AnchorPane.setTopAnchor(parentPane, 0.0);
                AnchorPane.setBottomAnchor(parentPane, 0.0);

            }
        });




        quickOptions.add(quickBtn1);
        quickOptions.add(quickBtn2);
        quickOptions.add(quickBtn3);
        quickOptions.add(quickBtn4);

        selectedFavs.add(null);
        selectedFavs.add(null);
        selectedFavs.add(null);
        selectedFavs.add(null);

        FXMLLoader fxmlLoader  =  new FXMLLoader(getClass().getResource("/view/component/quick_add_btn.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((QuickBtnController)(fxmlLoader.getController())).setDbc(this);

       loadNodesToQuickOptArr();
       setQuickNodesToPane();

    }
    private class MyKeyEventHandler implements EventHandler<KeyEvent> {

        final KeyCombination keyCombinationShiftC = new KeyCodeCombination(
                KeyCode.C, KeyCombination.CONTROL_DOWN);
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.A) {
                // Handle ESC key press
                System.out.println("A pressed");
            } else if(keyCombinationShiftC.match(event)){
                System.out.println("Key pressed: " + "CTRL + C Pressed");

            }else {
                System.out.println("Key pressed: " + event.getCode().toString());
            }
        }
    }

    private void setQuickNodesToPane() {
        for(int i = 0; i < quickOptions.size(); i++ ){

            quickOptions.get(i).getChildren().setAll(quickNodes.get(i));
        }
    }

    private void loadNodesToQuickOptArr() {
        quickNodes.clear();
        for (int i = 0; i < 4; i++) {
            if(selectedFavs.get(i) != null){
                switch (selectedFavs.get(i).toLowerCase()){
                    case "customer":
                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/customer_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "expense":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/expense_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "order":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/order_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "stock":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/stock_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    default:
                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/quick_add_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                }

            }else {
                try {
                    quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/quick_add_btn.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @FXML
    void selectionOkBtn(ActionEvent event) {

        selectedFavs.clear();
        quickSlectionPane.setVisible(false);

        if (cb1.isSelected()){
            if (! selectedFavs.contains(cb1.getText())){
                selectedFavs.add(cb1.getText());
            }
        }else {
            if ( selectedFavs.contains(cb1.getText())){
                selectedFavs.remove(cb1.getText());
            }
        }
        if (cb2.isSelected()){
            if (! selectedFavs.contains(cb2.getText())){
                selectedFavs.add(cb2.getText());
            }
        }else {
            if ( selectedFavs.contains(cb2.getText())){
                selectedFavs.remove(cb2.getText());
            }
        }
        if (cb3.isSelected()){
            if (! selectedFavs.contains(cb3.getText())){
                selectedFavs.add(cb3.getText());
            }
        }else {
            if ( selectedFavs.contains(cb3.getText())){
                selectedFavs.remove(cb3.getText());
            }
        }
        if (cb4.isSelected()){
            if (! selectedFavs.contains(cb4.getText())){
                selectedFavs.add(cb4.getText());
            }
        }else {
            if ( selectedFavs.contains(cb4.getText())){
                selectedFavs.remove(cb4.getText());
            }
        }
        if (cb5.isSelected()){
            if (! selectedFavs.contains(cb5.getText())){
                selectedFavs.add(cb5.getText());
            }
        }else {
            if ( selectedFavs.contains(cb5.getText())){
                selectedFavs.remove(cb5.getText());
            }
        }
        if (cb6.isSelected()){
            if (! selectedFavs.contains(cb6.getText())){
                selectedFavs.add(cb6.getText());
            }
        }else {
            if ( selectedFavs.contains(cb6.getText())){
                selectedFavs.remove(cb6.getText());
            }
        }
        System.out.println("\n\n");
        for (int i = 0; i < selectedFavs.size(); i++) {
            System.out.print(selectedFavs.get(i)+" - ");
        }

        for (int i = 0; i < 4; i++) {
            if(selectedFavs.size() < 4 ){
                selectedFavs.add(null);
            }
        }

        loadNodesToQuickOptArr();
        setQuickNodesToPane();
    }
    @FXML
    void editQuickBtn(ActionEvent event) {
        addFavFunc();
    }
    public void addFavFunc() {
        quickSlectionPane.setVisible(true);

    }
    @FXML
    void openMenuBtn(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader  =  new FXMLLoader(getClass().getResource("/view/menu_form.fxml"));
        Parent root  =  fxmlLoader.load();


        Scene scene = menuBtn.getScene();

        Window window = scene.getWindow();

        double windowWidth = window.getWidth();
        menuctl = ((MenuController)(fxmlLoader.getController()));
        menuctl.initData(parentPane);


        root.minWidth(windowWidth);

        root.translateYProperty().set(- (scene.getHeight()));
        parentPane.getChildren().add(root);




        Timeline timeline = new Timeline();
        KeyValue kv= new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2),kv);
        timeline.getKeyFrames().add(kf);
//        timeline.setOnFinished(event2 ->{
//            parentPane.getChildren().removeIf(node -> node != root);
//        });
        timeline.play();

    }
}
