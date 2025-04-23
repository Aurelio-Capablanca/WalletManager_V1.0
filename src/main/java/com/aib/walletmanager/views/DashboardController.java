package com.aib.walletmanager.views;

import com.aib.walletmanager.WalletApp;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    Label lblUserName;
    @FXML
    Label lblUserBalance;
    @FXML
    Button btnLogout;
    @FXML
    Button btnHistoric;
    @FXML
    Button btnManagement;
    @FXML
    Button btnInOut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSessionSignature signature = UserSessionSignature.getInstance(null);
        lblUserName.setText(new StringBuilder().append(signature.getUsersInstance().getNameUser())
                .append(" ")
                .append(signature.getUsersInstance().getLastNameUser()).toString());
        DecimalFormat formatDecimals = new DecimalFormat("####.00");
        lblUserBalance.setText(signature.getWalletsInstance().getIdWallet() == null ? "No Data in the System" : formatDecimals.format(signature.getWalletsInstance().getBalanceWallet()));
        btnManagement.setOnAction(actionEvent ->
                        UIActions.setNewStage(actionEvent, "ManagementBudgets.fxml", "Budget Management")
        );
        btnInOut.setOnAction(actionEvent ->
                UIActions.setNewStage(actionEvent, "IncomesOutcomes.fxml", "Incomes and Outcomes")
        );


    }

}
