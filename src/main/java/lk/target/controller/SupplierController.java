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
import lk.target.dto.SupplierDTO;
import lk.target.dto.tm.CustomerTM;
import lk.target.dto.tm.SupplierTM;
import lk.target.model.CustomerModel;
import lk.target.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
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
    private JFXTextField countryField;

    @FXML
    private JFXTextField provinceField1;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<SupplierTM> itemTable;

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

        SupplierDTO supplierDTO = new SupplierDTO(
                nextCodeField.getText(),
                titleField.getText(),
                nameField.getText(),
                addressField.getText(),
                mobileField.getText(),
                cityField.getText(),
                provinceField.getText(),
                countryField.getText()
        );


        try {
            Boolean saved = SupplierModel.save(supplierDTO);
            if (saved){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added!").show();
//                getAllItems();
//                itemTable.refresh();
                deleteBtn.setDisable(true);
                newBtn.setText("New Supplier");
                addBtn.setDisable(true);
                titleField.setDisable(true);
                nameField.setDisable(true);
                addressField.setDisable(true);
                mobileField.setDisable(true);
                cityField.setDisable(true);
                provinceField.setDisable(true);
                countryField.setDisable(true);

            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Supplier!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void itemSearchFieldAction(ActionEvent event) {

    }

    @FXML
    void newItemClick(ActionEvent event) {
        deleteBtn.setDisable(true);

        if (newBtn.getText().equalsIgnoreCase("new supplier")){
            updateBtn.setDisable(true);
            addBtn.setDisable(false);
            titleField.setDisable(false);
            nameField.setDisable(false);
            addressField.setDisable(false);
            mobileField.setDisable(false);
            cityField.setDisable(false);
            provinceField.setDisable(false);
            countryField.setDisable(false);

            try {
                String nextCode = SupplierModel.getNextCode();
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
            countryField.setText("");


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
            Boolean deleted = SupplierModel.delete(forSearch.getText());
            if (deleted){
                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
//                getAllItems();
//                itemTable.refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "Couldn't Delete Supplier").show();
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
        countryField.setDisable(true);




        String supplierCode = forSearch.getText();
        try {
            SupplierDTO supplier = SupplierModel.searchSupplier(supplierCode);
            if (supplier != null){


                titleField.setText(supplier.getTitle());
                nameField.setText(supplier.getName());
                addressField.setText(supplier.getAddress());
                mobileField.setText(supplier.getMobile());
                cityField.setText(supplier.getCity());
                provinceField.setText(supplier.getProvince());
                countryField.setText(supplier.getCountry());

                newBtn.setText("New supplier");
                nextCodeField.setText("");
                updateBtn.setDisable(false);
                updateBtn.setText("Edit");

            }
            else {
                new Alert(Alert.AlertType.ERROR, "No supplier With that id!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void updBtnClick(ActionEvent event) {
        if(updateBtn.getText().equalsIgnoreCase("Edit")){
            newBtn.setText("New Supplier");
            updateBtn.setText("Update");
            addBtn.setDisable(true);
//            forSearch.setDisable(true);
            titleField.setDisable(false);
            nameField.setDisable(false);
            addressField.setDisable(false);
            mobileField.setDisable(false);
            cityField.setDisable(false);
            provinceField.setDisable(false);
            countryField.setDisable(false);
        }else {



            SupplierDTO supplierDTO = new SupplierDTO(
                    forSearch.getText(),
                    titleField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    mobileField.getText(),
                    cityField.getText(),
                    provinceField.getText(),
                    countryField.getText()

            );

            try {
                Boolean updated = SupplierModel.update(supplierDTO);
                if (updated){
                    updateBtn.setText("Edit");
                    updateBtn.setDisable(true);
//                    forSearch.setDisable(false);
                    new Alert(Alert.AlertType.INFORMATION, "Supplier  "+supplierDTO.getId()+" has updated Successfully!").show();
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

    private List<SupplierTM> cusList = new ArrayList<>();

    private ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
    private void getAllItems() {
        obList.clear();
        try {
            cusList = SupplierModel.getAll();
            if (cusList.size() != 0){
                for(SupplierTM supplierTM: cusList){
                    obList.add(supplierTM);
                    itemTable.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Suppliers from database!").show();
        }
    }

    void setCellValueFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllItems();
        setCellValueFactory();
    }
}
