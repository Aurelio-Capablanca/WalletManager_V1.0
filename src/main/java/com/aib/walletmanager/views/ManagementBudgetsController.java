package com.aib.walletmanager.views;

import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.model.entities.WalletDuration;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagementBudgetsController implements Initializable {

    @FXML
    Button btnReturn;
    @FXML
    Button btnLogout;
    @FXML
    Button btnCreate;
    @FXML
    Button btnCancel;

    @FXML
    DatePicker dpEnd;
    @FXML
    DatePicker dpStart;
    @FXML
    TextField txtAmount;
    @FXML
    TextField txtPercentage;
    @FXML
    ComboBox<WalletDuration> cmbRepetition;
    @FXML
    ComboBox<WalletCategories> cmbCategory;
    @FXML
    TextField txtName;

    @FXML
    Label lblBalanceTotal;
    @FXML
    ListView<String> ltvEditables;


    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbCategory.setItems(FXCollections.observableList(signature.getWalletCategories()));
        cmbRepetition.setItems(FXCollections.observableList(signature.getWalletDurations()));

    }


}
