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
import lk.target.dto.ItemDTO;
import lk.target.dto.tm.ItemTM;
import lk.target.model.ItemModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    @FXML
    private JFXTextField itemCodeForSearch;

    @FXML
    private JFXTextField newItemCodeField;

    @FXML
    private JFXTextField itemName;

    @FXML
    private JFXTextField itemQty;

    @FXML
    private JFXTextField itemDesc;

    @FXML
    private JFXButton newItemBtn;

    @FXML
    private JFXTextField itemPrice;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private TableView<ItemTM> itemTable;
    private ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    private List<ItemTM> itemsList = new ArrayList<>();
    @FXML
    void onItemSearchBtn(ActionEvent event) {


        deleteBtn.setDisable(false);
        updateBtn.setDisable(true);
        addBtn.setDisable(true);
        itemName.setDisable(true);
        itemDesc.setDisable(true);
        itemPrice.setDisable(true);
        itemQty.setDisable(true);



        String itemCode = itemCodeForSearch.getText();
        try {
            ItemDTO item = ItemModel.searchItem(itemCode);
            if (item != null){


                itemName.setText(item.getName());
                itemDesc.setText(item.getDescription());
                itemQty.setText(String.valueOf(item.getQty()));
                itemPrice.setText(String.valueOf(item.getPrice()));
                newItemBtn.setText("New Item");
                newItemCodeField.setText("");
                updateBtn.setDisable(false);
                updateBtn.setText("Edit");

            }
            else {
                new Alert(Alert.AlertType.ERROR, "No Item With that code!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }@FXML
    void itemSearchFieldAction(ActionEvent event) {

        onItemSearchBtn(event);

    }


    @FXML
    void addBtnClick(ActionEvent event) {

        if (! itemQty.getText().matches("\\d+")){
            new Alert(Alert.AlertType.ERROR, "QTY should only contain numbers!").show();
            return;
        }
        if (! itemPrice.getText().matches("[0-9]{1,13}(\\.[0-9]*)?")){
            new Alert(Alert.AlertType.ERROR, "Price not valid!").show();
            return;
        }

        ItemDTO itemDTO = new ItemDTO(
                newItemCodeField.getText(),
                itemName.getText(),
                itemDesc.getText(),
                Integer.parseInt(itemQty.getText()),
                Double.parseDouble(itemPrice.getText())
        );


        try {
            Boolean saved = ItemModel.saveItem(itemDTO);
            if (saved){
                new Alert(Alert.AlertType.INFORMATION, "Item Added!").show();
                getAllItems();
                itemTable.refresh();
                deleteBtn.setDisable(true);
                newItemBtn.setText("New Item");
                addBtn.setDisable(true);
                itemName.setDisable(true);
                itemDesc.setDisable(true);
                itemPrice.setDisable(true);
                itemQty.setDisable(true);
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Item!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }


    }



    @FXML
    void newItemClick(ActionEvent event) {
        deleteBtn.setDisable(true);

        if (newItemBtn.getText().equalsIgnoreCase("new item")){
            updateBtn.setDisable(true);
            addBtn.setDisable(false);
            itemName.setDisable(false);
            itemDesc.setDisable(false);
            itemPrice.setDisable(false);
            itemQty.setDisable(false);

            try {
                String newItemCode = ItemModel.getNewItemCode();
                newItemCodeField.setText(newItemCode);
                itemCodeForSearch.setText("");
                updateBtn.setDisable(true);
                updateBtn.setText("Edit");
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }

            newItemBtn.setText("Clear");
        }else if(newItemBtn.getText().equalsIgnoreCase("clear")){
            itemName.setText("");
            itemDesc.setText("");
            itemPrice.setText("");
            itemQty.setText("");


        }

    }



    @FXML
    void updBtnClick(ActionEvent event) {
        if(updateBtn.getText().equalsIgnoreCase("Edit")){
            newItemBtn.setText("New Item");
            updateBtn.setText("Update");
            addBtn.setDisable(true);
            itemCodeForSearch.setDisable(true);
            itemName.setDisable(false);
            itemDesc.setDisable(false);
            itemPrice.setDisable(false);
            itemQty.setDisable(false);
        }else {



            ItemDTO itemDTO = new ItemDTO(
                    itemCodeForSearch.getText(),
                    itemName.getText(),
                    itemDesc.getText(),
                    Integer.parseInt(itemQty.getText()),
                    Double.parseDouble(itemPrice.getText())
            );

            try {
                Boolean updated = ItemModel.updateItem(itemDTO);
                if (updated){
                    updateBtn.setText("Edit");
                    updateBtn.setDisable(true);
                    itemCodeForSearch.setDisable(false);
                    new Alert(Alert.AlertType.INFORMATION, "Item "+itemDTO.getItemCode()+" has updated Successfully!").show();
                    getAllItems();
                    itemTable.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed. Try again!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
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
    JFXButton deleteBtn;

    @FXML
    void onDeleteClick(ActionEvent event) {

        try {
            Boolean deleted = ItemModel.deleteItem(itemCodeForSearch.getText());
            if (deleted){
                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                getAllItems();
                itemTable.refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "Couldn't Delete item").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

        deleteBtn.setDisable(true);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addBtn.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                Scene scene = addBtn.getScene();
                scene.getStylesheets().add(getClass().getResource("/style/TableStyles.css").toExternalForm());
            }
        });



        setCellValueFactory();
        getAllItems();


    }

    private void getAllItems() {
        obList.clear();
        try {
            itemsList = ItemModel.getAllItems();
            if (itemsList.size() != 0){
                for(ItemTM item: itemsList){
                    obList.add(item);
                    itemTable.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Items from database!").show();
        }
    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
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


    public void combCusOnAction(ActionEvent actionEvent) {
    }

    public void combItemOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderClick(ActionEvent actionEvent) {
    }
}
