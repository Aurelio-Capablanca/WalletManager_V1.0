package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.WalletBudgetLogic;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
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
    ListView<WalletOrganizations> ltvEditables;

    final WalletBudgetLogic budgetLogic = new WalletBudgetLogic();


    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblBalanceTotal.setText(signature.getWalletsInstance().getBalanceWallet().toString());
        cmbCategory.setItems(FXCollections.observableList(signature.getWalletCategories()));
        cmbRepetition.setItems(FXCollections.observableList(signature.getWalletDurations()));
        cmbCategory.setConverter(new StringConverter<WalletCategories>() {
            @Override
            public String toString(WalletCategories walletCategories) {
                return walletCategories != null ? walletCategories.getCategoryWallet() : " ";
            }

            @Override
            public WalletCategories fromString(String s) {
                return cmbCategory.getItems().stream().filter(values -> values.getCategoryWallet().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        });
        cmbRepetition.setConverter(new StringConverter<WalletDuration>() {
            @Override
            public String toString(WalletDuration walletDuration) {
                return walletDuration != null ? walletDuration.getTimeSet() : " ";
            }

            @Override
            public WalletDuration fromString(String s) {
                return cmbRepetition.getItems().stream().filter(value -> value.getTimeSet().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        });
        txtAmount.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtAmount.setText(oldValue); // block invalid input
            }
        });
        txtAmount.focusedProperty().addListener((observable, newValue, oldValue) -> {
            if (!newValue) {
                // Gained focus
                System.out.println("TextField focused!");
            } else {
                // Lost focus
                System.out.println("TextField lost focus!");
                BigDecimal userBalance = signature.getWalletsInstance().getBalanceWallet();
                BigDecimal percentage = new BigDecimal(txtAmount.getText()).divide(userBalance, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                txtPercentage.setText(percentage.toString());
            }
        });
        btnCreate.setOnAction(actionEvent -> {
            System.out.println(cmbCategory.getSelectionModel().getSelectedIndex());
            System.out.println(cmbRepetition.getSelectionModel().getSelectedIndex());
            Integer selectedCategory = 0;
            Integer selectedTime = 0;
            if (cmbCategory.getSelectionModel().getSelectedIndex() != -1)
                selectedCategory = cmbCategory.getItems().get(cmbCategory.getSelectionModel().getSelectedIndex()).getIdWalletCategory();
            if (cmbRepetition.getSelectionModel().getSelectedIndex() != -1)
                selectedTime = cmbRepetition.getItems().get(cmbRepetition.getSelectionModel().getSelectedIndex()).getIdDuration();
            WalletOrganizations orgObject = WalletOrganizations.builder()
                    .organizationName(txtName.getText())
                    .budgetAssigned(new BigDecimal(txtAmount.getText()))
                    .percentageFromWallet(Double.valueOf(txtPercentage.getText()))
                    .creationOrganization(LocalDateTime.now())
                    .idTimeSet(selectedTime)
                    .idWalletCategory(selectedCategory)
                    .startDuration(dpStart.getValue() == null ? null : dpStart.getValue().atStartOfDay())
                    .endDuration(dpEnd.getValue() == null ? null : dpEnd.getValue().atStartOfDay())
                    .idWallet(signature.getWalletsInstance().getIdWallet())
                    .build();
            System.out.println("Object to save : " + orgObject);
            budgetLogic.saveBudgets(orgObject);
        });
        ltvEditables.setItems(FXCollections.observableList(budgetLogic.findAll()));
        ltvEditables.setCellFactory(parameters -> new ListCell<WalletOrganizations>() {
            @Override
            protected void updateItem(WalletOrganizations item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getOrganizationName() + " - " + item.getBudgetAssigned() + "$ - " + item.getPercentageFromWallet() + " %");
                }
            }
        });
    }


}
