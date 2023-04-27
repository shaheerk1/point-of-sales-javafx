package lk.target.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.target.db.DBConnection;
import lk.target.dto.tm.ItemTM;
import lk.target.dto.tm.OrderTM;
import lk.target.model.ItemModel;
import lk.target.model.OrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AllOrdersController implements Initializable {


    @FXML
    private Label dateLbl;

    @FXML
    private TableView<OrderTM> allOrderTW;


    @FXML
    private TableColumn<?, ?> orderIdCol;

    @FXML
    private TableColumn<?, ?> timeCol;

    @FXML
    private TableColumn<?, ?> cusIdCol;

    @FXML
    private TableColumn<?, ?> detailCol;

    @FXML
    private TableColumn<?, ?> actionCol;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));
    }

    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();
    private List<OrderTM> orderList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValueFactory();
        getAllOrders();
    }


    private void getAllOrders() {
        obList.clear();
        try {
            orderList = OrderModel.getAllIOrders();
            if (orderList.size() != 0){
                for(OrderTM ord: orderList){
                    ord.getDetail().setOnAction((e) -> {
                        TableCell buttonCell = (TableCell) ((Button) e.getSource()).getParent();
                        int index = buttonCell.getIndex();
                        printReportofOrder(orderList.get(index));
                    });
                    setRemoveAction(ord.getAction());
                    obList.add(ord);
                    allOrderTW.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Orders from database!").show();
        }
    }

    private void setRemoveAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();



            if (result.orElse(no) == yes) {

                TableCell buttonCell = (TableCell) ((Button) e.getSource()).getParent();
                int index = buttonCell.getIndex();


                try {
                    boolean deleted = OrderModel.deleteOrder(obList.get(index).getId());

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                getAllOrders();
                allOrderTW.refresh();
            }

        });

    }

    private void printReportofOrder(OrderTM orderTM) {
        orderTM.getId();
        try {
            String lastId = OrderModel.getLastOrderId();
            InputStream rpt = PlaceOrderController.class.getResourceAsStream("/report/orderReport.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(rpt);
            Map<String,Object> data = new HashMap<>();
            data.put("orderIdParam", orderTM.getId());
            JasperPrint filledReport = JasperFillManager.fillReport(compileReport,data, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(filledReport,false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detail"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
    }
}
