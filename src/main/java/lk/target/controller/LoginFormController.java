package lk.target.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private Circle testCircle;

    public void initialize(){


    }

    @FXML
    void onLoginClick(ActionEvent event) throws IOException {


        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));

        stage.getIcons().add(new Image("/image/target.png"));
        stage.setResizable(true);
        stage.setTitle("Target");
        stage.centerOnScreen();
        stage.show();

    }


}
