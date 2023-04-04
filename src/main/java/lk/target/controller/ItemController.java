package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.target.dto.ItemDTO;
import lk.target.model.ItemModel;

import java.sql.SQLException;

public class ItemController {
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
    void onItemSearchBtn(ActionEvent event) {

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
                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed. Try again!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }


    }

}
