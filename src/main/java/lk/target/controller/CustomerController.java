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
import lk.target.dto.CustomerDTO;
import lk.target.dto.ItemDTO;
import lk.target.dto.tm.ItemTM;
import lk.target.model.CustomerModel;
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
        CustomerDTO customerDTO = new CustomerDTO(
                nextCodeField.getText(),
                titleField.getText(),
                nameField.getText(),
                addressField.getText(),
                mobileField.getText(),
                cityField.getText(),
                provinceField.getText()
        );


        try {
            Boolean saved = CustomerModel.saveItem(customerDTO);
            if (saved){
                new Alert(Alert.AlertType.INFORMATION, "Customer Added!").show();
//                getAllItems();
//                itemTable.refresh();
                deleteBtn.setDisable(true);
                newBtn.setText("New Customer");
                addBtn.setDisable(true);
                titleField.setDisable(true);
                nameField.setDisable(true);
                addressField.setDisable(true);
                mobileField.setDisable(true);
                cityField.setDisable(true);
                provinceField.setDisable(true);

            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Customer!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void itemSearchFieldAction(ActionEvent event) {

    }

    @FXML
    void newClick(ActionEvent event) {
        deleteBtn.setDisable(true);

        if (newBtn.getText().equalsIgnoreCase("new customer")){
            updateBtn.setDisable(true);
            addBtn.setDisable(false);
            titleField.setDisable(false);
            nameField.setDisable(false);
            addressField.setDisable(false);
            mobileField.setDisable(false);
            cityField.setDisable(false);
            provinceField.setDisable(false);

            try {
                String nextCode = CustomerModel.getNextCode();
                nextCodeField.setText(nextCode);
                forSearch.setText("");
                updateBtn.setDisable(true);
                updateBtn.setText("Edit");
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }

            newBtn.setText("Clear");
        }else if(newBtn.getText().equalsIgnoreCase("clear")){

            titleField.setText("");
            nameField.setText("");
            addressField.setText("");
            mobileField.setText("");
            cityField.setText("");
            provinceField.setText("");


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
    void onDeleteClick(ActionEvent event) {

        try {
            Boolean deleted = CustomerModel.delete(forSearch.getText());
            if (deleted){
                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
//                getAllItems();
//                itemTable.refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "Couldn't Delete Customer").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        deleteBtn.setDisable(true);


    }

    @FXML
    void onSearchBtn(ActionEvent event) {


        deleteBtn.setDisable(false);
        updateBtn.setDisable(true);
        addBtn.setDisable(true);
        titleField.setDisable(true);
        nameField.setDisable(true);
        addressField.setDisable(true);
        mobileField.setDisable(true);
        cityField.setDisable(true);
        provinceField.setDisable(true);




        String cusCode = forSearch.getText();
        try {
            CustomerDTO customer = CustomerModel.searchCustomer(cusCode);
            if (customer != null){


                titleField.setText(customer.getTitle());
                nameField.setText(customer.getName());
                addressField.setText(customer.getAddress());
                mobileField.setText(customer.getMobile());
                cityField.setText(customer.getCity());
                provinceField.setText(customer.getProvince());

                newBtn.setText("New Customer");
                nextCodeField.setText("");
                updateBtn.setDisable(false);
                updateBtn.setText("Edit");

            }
            else {
                new Alert(Alert.AlertType.ERROR, "No Customer With that id!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void updBtnClick(ActionEvent event) {
        if(updateBtn.getText().equalsIgnoreCase("Edit")){
            newBtn.setText("New Customer");
            updateBtn.setText("Update");
            addBtn.setDisable(true);
//            forSearch.setDisable(true);
            titleField.setDisable(false);
            nameField.setDisable(false);
            addressField.setDisable(false);
            mobileField.setDisable(false);
            cityField.setDisable(false);
            provinceField.setDisable(false);
        }else {



            CustomerDTO customerDTO = new CustomerDTO(
                    forSearch.getText(),
                    titleField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    mobileField.getText(),
                    cityField.getText(),
                    provinceField.getText()

            );

            try {
                Boolean updated = CustomerModel.update(customerDTO);
                if (updated){
                    updateBtn.setText("Edit");
                    updateBtn.setDisable(true);
//                    forSearch.setDisable(false);
                    new Alert(Alert.AlertType.INFORMATION, "Customer  "+customerDTO.getId()+" has updated Successfully!").show();
//                    getAllItems();
//                    itemTable.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed. Try again!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }


    }
}
