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
import lk.target.dto.tm.SuppliesTM;
import lk.target.model.OrderModel;
import lk.target.model.SupplyModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AllSuppliesController implements Initializable {

    @FXML
    private Label dateLbl;

    @FXML
    private TableView<SuppliesTM> suppliesTV;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> timeCol;

    @FXML
    private TableColumn<?, ?> supIdCol;

    @FXML
    private TableColumn<?, ?> isPaidCol;

    @FXML
    private TableColumn<?, ?> amountCol;

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


    private ObservableList<SuppliesTM> obList = FXCollections.observableArrayList();
    private List<SuppliesTM> supplyList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValueFactory();
        getAllSupplies();
    }


    private void getAllSupplies() {
        obList.clear();
        try {
            supplyList = SupplyModel.getAllIOrders();
            if (supplyList.size() != 0){
                for(SuppliesTM sup: supplyList){


                    sup.getDetail().setOnAction((e) -> {
                        TableCell buttonCell = (TableCell) ((Button) e.getSource()).getParent();
                        int index = buttonCell.getIndex();
                        printReportOfOrder(supplyList.get(index));
                    });
                    setRemoveAction(sup.getAction());
                    obList.add(sup);
                    suppliesTV.setItems(obList);

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
                    boolean deleted = SupplyModel.deleteSupply(obList.get(index).getId());

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                getAllSupplies();
                suppliesTV.refresh();
            }

        });

    }

    private void printReportOfOrder(SuppliesTM supTM) {

//        try {
//            InputStream rpt = PlaceOrderController.class.getResourceAsStream("/report/orderReport.jrxml");
//            JasperReport compileReport = JasperCompileManager.compileReport(rpt);
//            Map<String,Object> data = new HashMap<>();
//            data.put("orderIdParam", supTM.getId());
//            JasperPrint filledReport = JasperFillManager.fillReport(compileReport,data, DBConnection.getInstance().getConnection());
//            JasperViewer.viewReport(filledReport,false);
//
//        } catch (JRException | SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    void setCellValueFactory() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        supIdCol.setCellValueFactory(new PropertyValueFactory<>("supId"));
        isPaidCol.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detail"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

}
