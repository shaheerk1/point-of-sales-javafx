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
import lk.target.db.DBConnection;
import lk.target.dto.ItemDTO;
import lk.target.dto.CartDTO;
import lk.target.dto.tm.PlaceOrderTM;
import lk.target.model.CustomerModel;
import lk.target.model.ItemModel;
import lk.target.model.OrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    private JFXTextField itemCodeForSearch;

    @FXML
    private JFXTextField cusCodeForSearch;
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
    void btnPrintOnAction(ActionEvent event) {



        try {
            String lastId = OrderModel.getLastOrderId();
            InputStream rpt = PlaceOrderController.class.getResourceAsStream("/report/orderReport.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(rpt);
            Map<String,Object> data = new HashMap<>();
            data.put("orderIdParam", lastId);
            JasperPrint filledReport = JasperFillManager.fillReport(compileReport,data, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(filledReport,false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private JFXButton placeOrderBtn;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();



            if (result.orElse(no) == yes) {

                TableCell buttonCell = (TableCell) ((Button) e.getSource()).getParent();
                int index = buttonCell.getIndex();

                obList.remove(index);

                orderTable.refresh();
                calculateNetTotal();
            }

        });
    }

    @FXML
    void addBtnClick(ActionEvent event) {

        if (! qtyField.getText().matches("\\d+")){
            new Alert(Alert.AlertType.ERROR, "QTY should only contain numbers!").show();
            return;
        }

        String code = itemComboBox.getValue();
        String name = itemNameLbl.getText();
        String description = DescLbl.getText();
        int qty = Integer.parseInt(qtyField.getText());
        double unitPrice = Double.parseDouble(unitPriceLbl.getText());
        double total = qty * unitPrice;
        Button btnRemove = new Button("Remove");
        setRemoveBtnOnAction(btnRemove);
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
        itemCodeForSearch.setText("");
        itemCodeForSearch.requestFocus();
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
                generateNextOrderId();
                obList.clear();
                orderTable.refresh();
                itemNameLbl.setText("");
                DescLbl.setText("");
                unitPriceLbl.setText("");
                cusCodeForSearch.requestFocus();

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

        itemCodeForSearch.requestFocus();
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
       if(item != null){
           itemNameLbl.setText(item.getName());
           DescLbl.setText(item.getDescription());
           unitPriceLbl.setText(String.valueOf(item.getPrice()));
       }
    }

    @FXML
    void itemSearchFieldAction(ActionEvent event) {

        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.getCodes(itemCodeForSearch.getText());

            for (String code : codes) {
                obList.add(code);
            }
            if(obList.size() > 0){
                itemComboBox.setItems(obList);
            }else {
                itemComboBox.setItems(null);
            }
            itemComboBox.requestFocus();
            itemComboBox.show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
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
        cusCodeForSearch.requestFocus();
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

    public void allOrdersClick(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/all_orders_form.fxml"));
        stage.setScene(new Scene(root));
    }
    public void cusSearchFieldAction(ActionEvent actionEvent) {
        try {
            List<String> ids = CustomerModel.getIds(cusCodeForSearch.getText());

            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }

            if(obList.size() > 0){
                cusComboBox.setItems(obList);
            }else {
                cusComboBox.setItems(null);
            }
            cusComboBox.requestFocus();
            cusComboBox.show();


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void qtyOnAction(ActionEvent actionEvent) {
        addBtnClick(actionEvent);

    }
}
