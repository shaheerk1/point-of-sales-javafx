package lk.target.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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

public class SupplyController {

    @FXML
    private Label orderNumber;

    @FXML
    private Label nextId;

    @FXML
    private Label dateLbl;

    @FXML
    private JFXComboBox<?> supComboBox;

    @FXML
    private Label cusNameLbl;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXTextField itemCodeForSearch;

    @FXML
    private JFXComboBox<?> itemComboBox;

    @FXML
    private Label itemNameLbl;

    @FXML
    private Label DescLbl;

    @FXML
    private Label unitPriceLbl;

    @FXML
    private JFXTextField qtyField;

    @FXML
    private TableView<?> orderTable;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemDescription;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colItemPrice;

    @FXML
    private TableColumn<?, ?> colTot;

    @FXML
    private TableColumn<?, ?> colItemAction;

    @FXML
    private Label totLbl;

    @FXML
    private JFXButton placeOrderBtn;

    @FXML
    void addBtnClick(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void combCusOnAction(ActionEvent event) {

    }

    @FXML
    void combItemOnAction(ActionEvent event) {

    }

    @FXML
    void itemSearchFieldAction(ActionEvent event) {

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void onItemSearchBtn(ActionEvent event) {

    }
}
