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
import lk.target.dto.tm.ItemTM;
import lk.target.dto.tm.OrderTM;
import lk.target.dto.tm.StockTM;
import lk.target.model.ItemModel;
import lk.target.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    @FXML
    void onBack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));
    }

    private ObservableList<StockTM> obList = FXCollections.observableArrayList();
    private List<ItemTM> stockList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void getAll() {
        obList.clear();


        try {
            stockList = ItemModel.getAllItems();
            if (stockList.size() != 0){
                for(ItemTM stk: stockList){
                    Label noStock = new Label("No Stock");
                    Label lowStock = new Label("Low Stock");
                    Label hasStock = new Label("Stock available");


                    noStock.setStyle("-fx-background-color: #FF6F41;");
                    lowStock.setStyle("-fx-background-color: #FFE141;");
                    hasStock.setStyle("-fx-background-color: #73FF41;");
                    StockTM stktm = new StockTM(
                            stk.getItemCode(),
                            stk.getName(),
                            stk.getQty(),
                            (stk.getQty() <= 0) ? noStock: (stk.getQty() < 10) ? lowStock: hasStock
                    );
                    obList.add(stktm);
                    stockTV.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Stock from database!").show();
        }
    }

    @FXML
    private Label dateLbl;

    @FXML
    private TableView<StockTM> stockTV;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> qtyOnHandCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    void setCellValueFactory() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyOnHandCol.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
