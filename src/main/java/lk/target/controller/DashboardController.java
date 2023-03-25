package lk.target.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane quickBtn1;

    @FXML
    private AnchorPane quickBtn2;

    @FXML
    private AnchorPane quickBtn3;

    @FXML
    private AnchorPane quickBtn4;

    @FXML
    private JFXCheckBox cb1;

    @FXML
    private JFXCheckBox cb2;

    @FXML
    private JFXCheckBox cb3;

    @FXML
    private JFXCheckBox cb4;

    @FXML
    private JFXCheckBox cb5;

    @FXML
    private JFXCheckBox cb6;

    @FXML
    private AnchorPane quickSlectionPane;

    List<AnchorPane> quickOptions= new ArrayList<>();
    List<Node> quickNodes= new ArrayList<>();

    private ArrayList<String> selectedFavs= new ArrayList<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quickOptions.add(quickBtn1);
        quickOptions.add(quickBtn2);
        quickOptions.add(quickBtn3);
        quickOptions.add(quickBtn4);

        selectedFavs.add(null);
        selectedFavs.add(null);
        selectedFavs.add(null);
        selectedFavs.add(null);

        FXMLLoader fxmlLoader  =  new FXMLLoader(getClass().getResource("/view/component/quick_add_btn.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((QuickBtnController)(fxmlLoader.getController())).setDbc(this);

       loadNodesToQuickOptArr();
       setQuickNodesToPane();

    }

    private void setQuickNodesToPane() {
        for(int i = 0; i < quickOptions.size(); i++ ){

            quickOptions.get(i).getChildren().setAll(quickNodes.get(i));
        }
    }

    private void loadNodesToQuickOptArr() {
        quickNodes.clear();
        for (int i = 0; i < 4; i++) {
            if(selectedFavs.get(i) != null){
                switch (selectedFavs.get(i).toLowerCase()){
                    case "customer":
                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/customer_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "expense":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/expense_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "order":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/order_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "stock":

                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/stock_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    default:
                        try {
                            quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/quick_add_btn.fxml")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                }

            }else {
                try {
                    quickNodes.add((Node) FXMLLoader.load(getClass().getResource("/view/component/quick_add_btn.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @FXML
    void selectionOkBtn(ActionEvent event) {

        selectedFavs.clear();
        quickSlectionPane.setVisible(false);

        if (cb1.isSelected()){
            if (! selectedFavs.contains(cb1.getText())){
                selectedFavs.add(cb1.getText());
            }
        }else {
            if ( selectedFavs.contains(cb1.getText())){
                selectedFavs.remove(cb1.getText());
            }
        }
        if (cb2.isSelected()){
            if (! selectedFavs.contains(cb2.getText())){
                selectedFavs.add(cb2.getText());
            }
        }else {
            if ( selectedFavs.contains(cb2.getText())){
                selectedFavs.remove(cb2.getText());
            }
        }
        if (cb3.isSelected()){
            if (! selectedFavs.contains(cb3.getText())){
                selectedFavs.add(cb3.getText());
            }
        }else {
            if ( selectedFavs.contains(cb3.getText())){
                selectedFavs.remove(cb3.getText());
            }
        }
        if (cb4.isSelected()){
            if (! selectedFavs.contains(cb4.getText())){
                selectedFavs.add(cb4.getText());
            }
        }else {
            if ( selectedFavs.contains(cb4.getText())){
                selectedFavs.remove(cb4.getText());
            }
        }
        if (cb5.isSelected()){
            if (! selectedFavs.contains(cb5.getText())){
                selectedFavs.add(cb5.getText());
            }
        }else {
            if ( selectedFavs.contains(cb5.getText())){
                selectedFavs.remove(cb5.getText());
            }
        }
        if (cb6.isSelected()){
            if (! selectedFavs.contains(cb6.getText())){
                selectedFavs.add(cb6.getText());
            }
        }else {
            if ( selectedFavs.contains(cb6.getText())){
                selectedFavs.remove(cb6.getText());
            }
        }
        System.out.println("\n\n");
        for (int i = 0; i < selectedFavs.size(); i++) {
            System.out.print(selectedFavs.get(i)+" - ");
        }

        for (int i = 0; i < 4; i++) {
            if(selectedFavs.size() < 4 ){
                selectedFavs.add(null);
            }
        }

        loadNodesToQuickOptArr();
        setQuickNodesToPane();
    }
    @FXML
    void editQuickBtn(ActionEvent event) {
        addFavFunc();
    }
    public void addFavFunc() {
        quickSlectionPane.setVisible(true);

    }
}
