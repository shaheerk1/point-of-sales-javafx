package lk.target.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.target.dto.ExpenseDTO;
import lk.target.dto.tm.ExpenseTM;
import lk.target.dto.tm.ReturnTM;
import lk.target.model.ExpenseModel;
import lk.target.model.ReturnModel;
import lk.target.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {

    @FXML
    private Label nextId;

    @FXML
    private JFXComboBox<String> typeComb;

    @FXML
    private JFXTextField descField;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXTextField returnIdField;

    @FXML
    private JFXTextField supIdField;

    @FXML
    private JFXTextField itemCodeField;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<ExpenseTM> itemTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> descCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> timeCol;
    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> itemCodeCol;

    @FXML
    private TableColumn<?, ?> supCol;

    @FXML
    private TableColumn<?, ?> returnCol;

    @FXML
    void addBtnClick(ActionEvent event) {

        ExpenseDTO expenseDTO = new ExpenseDTO(
                null,
                descField.getText(),
                Double.parseDouble(amountField.getText()),
                typeComb.getValue(),
                itemCodeField.getText(),
                supIdField.getText(),
                returnIdField.getText()
        );

        try {
            Boolean saved = ExpenseModel.save(expenseDTO);
            if (saved){
                new Alert(Alert.AlertType.INFORMATION, "Expense Added!").show();
//                getAllItems();
//                itemTable.refresh();
                deleteBtn.setDisable(true);


            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Expense!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            System.out.println(e.getMessage());
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

    }

    @FXML
    void updBtnClick(ActionEvent event) {

    }

    void loadReturnTypes(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        ArrayList<String> types = new ArrayList<>(List.of("Repair", "Supply","Return", "Other"));


        for (String type : types) {

            obList.add(type);
        }
        typeComb.setItems(obList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadReturnTypes();
        generateNextId();
        getAllItems();
        setCellValueFactory();
    }

    private List<ExpenseTM> expenseList = new ArrayList<>();
    private ObservableList<ExpenseTM> obList = FXCollections.observableArrayList();


    private void getAllItems() {
        obList.clear();
        try {
            expenseList = ExpenseModel.getAll();
            if (expenseList.size() != 0){
                for(ExpenseTM expenseTM: expenseList){
                    obList.add(expenseTM);
                    itemTable.setItems(obList);

                }
            }else {
                //no Items in db
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to get Expenses from database!").show();
        }
    }

    void setCellValueFactory() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        itemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        supCol.setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        returnCol.setCellValueFactory(new PropertyValueFactory<>("returnId"));
    }

    private void generateNextId() {
//        try {
//            String next = ExpenseModel.generateNextId();
//            nextId.setText(next);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
