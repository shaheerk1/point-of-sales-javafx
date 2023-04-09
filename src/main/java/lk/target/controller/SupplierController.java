package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplierController {
    @FXML
    private JFXTextField forSearch;

    @FXML
    private JFXTextField nextCodeField;

    @FXML
    private JFXTextField titleField;

    @FXML
    private JFXTextField addressField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXTextField mobileField;

    @FXML
    private JFXTextField cityField;

    @FXML
    private JFXTextField provinceField;

    @FXML
    private JFXTextField provinceField1;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<?> itemTable;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private TableColumn<?, ?> mobile;

    @FXML
    void addBtnClick(ActionEvent event) {

    }

    @FXML
    void itemSearchFieldAction(ActionEvent event) {

    }

    @FXML
    void newItemClick(ActionEvent event) {

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
    void onSearchBtn(ActionEvent event) {

    }

    @FXML
    void updBtnClick(ActionEvent event) {

    }

}
