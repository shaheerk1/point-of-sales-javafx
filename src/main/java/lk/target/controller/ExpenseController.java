package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseController {

    @FXML
    private Label nextId;

    @FXML
    private JFXComboBox<?> typeComb;

    @FXML
    private JFXTextField descField;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXTextField returnIdField;

    @FXML
    private JFXTextField supIdField;

    @FXML
    private JFXTextField itemCodeField;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<?> itemTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> timeCol;

    @FXML
    private TableColumn<?, ?> itemCodeCol;

    @FXML
    private TableColumn<?, ?> supCol;

    @FXML
    private TableColumn<?, ?> returnCol;

    @FXML
    void addBtnClick(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void onDeleteClick(ActionEvent event) {

    }

    @FXML
    void updBtnClick(ActionEvent event) {

    }

}
