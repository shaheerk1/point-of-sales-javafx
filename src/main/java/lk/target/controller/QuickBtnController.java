package lk.target.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class QuickBtnController {

    private static DashboardController dbc;

    @FXML
    void addFavBtnClick(ActionEvent event) {
        if(dbc != null){
            dbc.addFavFunc();
        }
    }

    public void setDbc(DashboardController dbc){
        this.dbc = dbc;
    }

}
