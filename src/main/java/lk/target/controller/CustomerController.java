package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.target.dto.ItemDTO;
import lk.target.dto.tm.ItemTM;
import lk.target.model.ItemModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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
