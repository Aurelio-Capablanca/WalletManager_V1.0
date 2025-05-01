package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.WalletBudgetLogic;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManagementBudgetsController implements Initializable {

    @FXML
    Button btnReturn;
    @FXML
    Button btnLogout;
    @FXML
    Button btnCreate;
    @FXML
    Button btnUpdate;
    @FXML
    Button btnDelete;
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
    ListView<WalletOrganizations> ltvEditable;

    private final WalletBudgetLogic budgetLogic = new WalletBudgetLogic();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);
    private WalletOrganizations selectedValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblBalanceTotal.setText(signature.getWalletsInstance().getBalanceWallet().toString() + " $");
        ltvEditable.setItems(FXCollections.observableList(budgetLogic.findAll()));
        cmbCategory.setItems(FXCollections.observableList(signature.getWalletCategories()));
        cmbRepetition.setItems(FXCollections.observableList(signature.getWalletDurations()));
        txtPercentage.setDisable(true);
        btnReturn.setOnAction(actionEvent -> UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard"));
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
                txtAmount.setText(oldValue);
            }
        });
        txtAmount.focusedProperty().addListener((observable, newValue, oldValue) -> {
            if (newValue) {
                BigDecimal userBalance = signature.getWalletsInstance().getBalanceWallet();
                BigDecimal percentage = new BigDecimal(txtAmount.getText()).divide(userBalance, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                txtPercentage.setText(percentage.toString());
            }
        });
        btnCreate.setOnAction(actionEvent -> {
            WalletOrganizations orgObject = objectComposition(null);
            budgetLogic.saveBudgets(orgObject);
            resetForm();
        });
        ltvEditable.setCellFactory(parameters -> new ListCell<WalletOrganizations>() {
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
        ltvEditable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            selectedValue = newValue == null ? oldValue : newValue;
            DecomposeObject(selectedValue);
        }));
        ltvEditable.setOnKeyPressed(keyEvent -> {
            System.out.println("Code : " + keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.S) {
                System.out.println("Enters!!!!");
                if (selectedValue != null)
                    budgetLogic.deleteEntities(selectedValue);
                ltvEditable.setItems(FXCollections.observableList(budgetLogic.findAll()));
            }
        });
        btnUpdate.setOnAction(actionEvent -> {
            WalletOrganizations orgObject = objectComposition(selectedValue.getIdWalletOrganization());
            budgetLogic.saveBudgets(orgObject);
            resetForm();
        });
        btnDelete.setOnAction(actionEvent -> System.out.println("Please Press DELETE or S to delete the element selected from the List"));
    }

    private void resetForm() {
        ltvEditable.setItems(FXCollections.observableList(budgetLogic.findAll()));
        cleanBudgetForms();
    }

    private void cleanBudgetForms() {
        UIActions.cleanUIForms(txtName);
        UIActions.cleanUIForms(txtAmount);
        UIActions.cleanUIForms(txtPercentage);
        UIActions.cleanUIForms(dpEnd);
        UIActions.cleanUIForms(dpStart);
        UIActions.cleanUIForms(cmbRepetition);
        UIActions.cleanUIForms(cmbCategory);
    }

    private void DecomposeObject(WalletOrganizations item) {
        cmbCategory.getItems().stream()
                .filter(cat -> Objects.equals(cat.getIdWalletCategory(), item.getIdWalletCategory()))
                .findFirst()
                .ifPresent(cmbCategory::setValue);
        cmbRepetition.getItems().stream()
                .filter(dur -> Objects.equals(dur.getIdDuration(), item.getIdTimeSet()))
                .findFirst()
                .ifPresent(cmbRepetition::setValue);
        txtName.setText(item.getOrganizationName());
        txtAmount.setText(item.getBudgetAssigned().toString());
        txtPercentage.setText(item.getPercentageFromWallet().toString());
        dpEnd.setValue(item.getEndDuration() == null ? null : item.getEndDuration().toLocalDate());
        dpStart.setValue(item.getStartDuration() == null ? null : item.getStartDuration().toLocalDate());
    }

    private WalletOrganizations objectComposition(Integer id) {
        Integer selectedCategory = cmbCategory.getSelectionModel().getSelectedIndex() != -1 ? cmbCategory.getItems().get(cmbCategory.getSelectionModel().getSelectedIndex()).getIdWalletCategory() : 0;
        Integer selectedTime = cmbRepetition.getSelectionModel().getSelectedIndex() != -1 ? cmbRepetition.getItems().get(cmbRepetition.getSelectionModel().getSelectedIndex()).getIdDuration() : 0;
        return WalletOrganizations.builder()
                .idWalletOrganization(id).organizationName(txtName.getText()).budgetAssigned(new BigDecimal(txtAmount.getText()))
                .percentageFromWallet(Double.valueOf(txtPercentage.getText())).creationOrganization(LocalDateTime.now()).idTimeSet(selectedTime).idWalletCategory(selectedCategory)
                .startDuration(dpStart.getValue() == null ? null : dpStart.getValue().atStartOfDay())
                .endDuration(dpEnd.getValue() == null ? null : dpEnd.getValue().atStartOfDay()).idWallet(signature.getWalletsInstance().getIdWallet())
                .build();
    }
}