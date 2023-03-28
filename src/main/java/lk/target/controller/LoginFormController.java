package lk.target.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lk.target.dto.EmployeeDTO;
import lk.target.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private Circle testCircle;


    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXTextField passwordField;


    public void initialize(){


    }

    @FXML
    void onLoginClick(ActionEvent event) throws IOException {

        String uname = userNameField.getText();
        String pword = passwordField.getText();

        try {
            EmployeeDTO emp = EmployeeModel.checkCredentials(uname,pword);
            if(emp != null && uname.equals(emp.getUsername()) && pword.equals(emp.getPassword())){

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                stage.setScene(new Scene(root));

                stage.getIcons().add(new Image("/image/target.png"));
                stage.setResizable(true);
                stage.setTitle("Target");
                stage.centerOnScreen();
                stage.show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Wrong Credentials, try again!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }



    }


}
