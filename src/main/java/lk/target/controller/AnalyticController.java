package lk.target.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.target.dto.StarCustomerDTO;
import lk.target.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AnalyticController implements Initializable {

    @FXML
    private Label cusId0;

    @FXML
    private Label cusAmount0;

    @FXML
    private Label cusId1;

    @FXML
    private Label cusAmount1;

    @FXML
    private Label cusId2;

    @FXML
    private Label cusAmount2;

    @FXML
    private Label cusId3;

    @FXML
    private Label cusAmount3;

    @FXML
    private Label cusId4;

    @FXML
    private Label cusAmount4;

    @FXML
    private Label dateLbl;

    @FXML
    private Label totMonth;

    @FXML
    private Label mostActCus;
    @FXML
    void onBack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root  =  FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        stage.setScene(new Scene(root));
    }
    @FXML
    private Label totExpMonth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            Double monthTotSale = OrderModel.getMonthTotSale();
            if (monthTotSale!= null){
                String stringPrice = String.format("%.2f", monthTotSale);
                totMonth.setText("RS "+ stringPrice);
            }

            Double monthTotExp = OrderModel.getMonthTotExp();
            if (monthTotExp!= null){
                String stringPrice = String.format("%.2f", monthTotExp);
                totExpMonth.setText("RS "+ stringPrice);
            }
            List<StarCustomerDTO> strCus = OrderModel.getBestCus();

            if (strCus.size() > 0){
                cusId0.setText(strCus.get(0).getId());
                cusAmount0.setText("RS "+ strCus.get(0).getAmount());

                cusId1.setText(strCus.get(1).getId());
                cusAmount1.setText("RS "+ strCus.get(1).getAmount());

                cusId2.setText(strCus.get(2).getId());
                cusAmount2.setText("RS "+ strCus.get(2).getAmount());

                cusId3.setText(strCus.get(3).getId());
                cusAmount3.setText("RS "+ strCus.get(3).getAmount());

                cusId4.setText(strCus.get(4).getId());
                cusAmount4.setText("RS "+ strCus.get(4).getAmount());
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
