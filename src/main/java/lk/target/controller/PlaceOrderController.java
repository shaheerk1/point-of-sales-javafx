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
import lk.target.dto.ItemDTO;
import lk.target.dto.tm.CartDTO;
import lk.target.dto.tm.PlaceOrderTM;
import lk.target.model.CustomerModel;
import lk.target.model.ItemModel;
import lk.target.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    @FXML
    private Label orderNumber;

    @FXML
    private Label orderIdLbl;

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
    private Label dateTimeLbl;

    @FXML
    private Label DescLbl;

    @FXML
    private Label unitPriceLbl;

    @FXML
    private JFXTextField qtyField;

    @FXML
    private TableView<PlaceOrderTM> orderTable;

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

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    @FXML
    void addBtnClick(ActionEvent event) {
        String code = itemComboBox.getValue();
        String name = itemNameLbl.getText();
        String description = DescLbl.getText();
        int qty = Integer.parseInt(qtyField.getText());
        double unitPrice = Double.parseDouble(unitPriceLbl.getText());
        double total = qty * unitPrice;
        Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

//        setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

        if (!obList.isEmpty()) {
            for (int i = 0; i < orderTable.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    qty += (int) colItemQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    orderTable.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        PlaceOrderTM tm = new PlaceOrderTM(code, name,description, qty, unitPrice, total, btnRemove);

        obList.add(tm);
        orderTable.setItems(obList);
        calculateNetTotal();

        qtyField.setText("");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String oId = orderIdLbl.getText();
        String cusId = cusComboBox.getValue();

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < orderTable.getItems().size(); i++) {
            PlaceOrderTM tm = obList.get(i);

            CartDTO cartDTO = new CartDTO(tm.getCode(), tm.getQty(), tm.getPrice());
            cartDTOList.add(cartDTO);
        }

        try {
            boolean isPlaced = OrderModel.placeOrder(oId, cusId, cartDTOList);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < orderTable.getItems().size(); i++) {
            double total  = (double) colTot.getCellData(i);
            netTotal += total;
        }
        totLbl.setText(String.valueOf(netTotal));
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
        unitPriceLbl.setText(String.valueOf(item.getPrice()));
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
        setCellValueFactory();
        setOrderDateTime();
        loadCustomerIds();
        loadItemCodes();
        generateNextOrderId();
    }

    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            orderIdLbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private void setOrderDateTime() {
        dateTimeLbl.setText(String.valueOf(LocalDate.now()));
    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("total"));
        colItemAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }
}
