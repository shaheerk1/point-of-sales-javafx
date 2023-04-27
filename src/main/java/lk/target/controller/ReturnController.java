package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.target.dto.CustomerDTO;
import lk.target.dto.ItemDTO;
import lk.target.dto.ReturnDTO;
import lk.target.dto.tm.ReturnTM;
import lk.target.dto.tm.SupplierTM;
import lk.target.dto.tm.SupplyTM;
import lk.target.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    @FXML
    private Label orderNumber;

    @FXML
    private Label nextId;

    @FXML
    private Label dateTimeLbl;

    @FXML
    private JFXComboBox<String> cusComboBox;

    @FXML
    private Label cusNameLbl;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXTextField itemCodeForSearch1;

    @FXML
    private JFXComboBox<String> itemComboBox;

    @FXML
    private Label itemNameLbl;

    @FXML
    private Label DescLbl;

    @FXML
    private JFXTextField qtyField;

    @FXML
    private JFXTextField orderIdField;

    @FXML
    private TableView<ReturnTM> orderTable;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colItemAction;

    @FXML
    private Label totLbl;

    @FXML
    void addBtnClick(ActionEvent event) {

        ReturnDTO returnDTO = new ReturnDTO(
                nextId.getText(),
                itemComboBox.getValue(),
                orderIdField.getText(),
                Integer.parseInt(qtyField.getText())
        );


        try {
            Boolean saved = ReturnModel.saveItem(returnDTO);
            if (saved){
                new Alert(Alert.AlertType.INFORMATION, "Return Added!").show();
                generateNextSupplyId();

//                getAllItems();
//                itemTable.refresh();

            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Return!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }



    @FXML
    void combCusOnAction(ActionEvent event) {

    }

    @FXML
    void combItemOnAction(ActionEvent event) {
        String code = itemComboBox.getSelectionModel().getSelectedItem();

        try {
            ItemDTO item = ItemModel.searchById(code);
            fillItemFields(item);
            qtyField.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }


    }
    private void fillItemFields(ItemDTO item) {
        itemNameLbl.setText(item.getName());
        DescLbl.setText(item.getDescription());
    }

    private void generateNextSupplyId() {
        try {
            String next = ReturnModel.generateNextId();
            nextId.setText(next);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            List<String> ids = CustomerModel.getIds();

            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cusComboBox.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadItemCodes() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.getCodes();

            for (String code : codes) {
                obList.add(code);
            }
            itemComboBox.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextSupplyId();
        loadCustomerIds();
        loadItemCodes();
        getAllItems();
        setCellValueFactory();
    }

    private List<ReturnTM> returnList = new ArrayList<>();
    private ObservableList<ReturnTM> obList = FXCollections.observableArrayList();


    private void getAllItems() {
        obList.clear();
        try {
            returnList = ReturnModel.getAll();
            if (returnList.size() != 0){
                for(ReturnTM returnTM: returnList){
                    obList.add(returnTM);
                    orderTable.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Returns from database!").show();
        }
    }

    void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colItemAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }


}
