package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.IncomeOutcomeLogic;
import com.aib.walletmanager.business.logic.WalletBudgetLogic;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.DTO.BalanceOrigins;
import com.aib.walletmanager.model.DTO.IncomeOutcomeCategories;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class IncomesOutcomesController implements Initializable {

    @FXML
    Button btnReturn;
    @FXML
    Button btnLogout;
    @FXML
    TreeView<String> treeView;
    @FXML
    ComboBox<IncomeOutcomeCategories> cmbCategory;
    @FXML
    TextField txtAmount;
    @FXML
    RadioButton rdbOutcomeActivation;
    @FXML
    Button btnSave;
    @FXML
    Button btnCancel;
    @FXML
    TextArea txaMotive;
    @FXML
    Label lblTypeAction;
    @FXML
    Label lblAmountDischarge;
    @FXML
    RadioButton lblIsWallet;


    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);
    private final WalletBudgetLogic budgetLogic = new WalletBudgetLogic();
    private final IncomeOutcomeLogic inOutLogic = new IncomeOutcomeLogic();
    private final BalanceOrigins origins = new BalanceOrigins();
    private WalletOrganizations selectedItem;
    private WalletOrganizations middlePoint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final List<WalletOrganizations> walletOrg = budgetLogic.findAll();
        lblAmountDischarge.setText("0.00 $");
        btnReturn.setOnAction(actionEvent -> UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard"));
        cmbCategory.setItems(FXCollections.observableList(signature.getTypeIncomes().stream().map(values -> IncomeOutcomeCategories.builder().id(values.getIdTypeIncome()).value(values.getTypeIncome()).build()).collect(Collectors.toList())));
        lblTypeAction.setText("Incomes");
        cmbCategory.setConverter(new StringConverter<IncomeOutcomeCategories>() {
            @Override
            public String toString(IncomeOutcomeCategories categories) {

                return categories != null ? categories.getValue() : "";
            }

            @Override
            public IncomeOutcomeCategories fromString(String s) {
                return cmbCategory.getItems().stream().filter(values -> values.getValue().equalsIgnoreCase(s)).findFirst().orElse(null);
            }
        });
        rdbOutcomeActivation.setOnAction(actionEvent -> {
            System.out.println("Is Selected :" + rdbOutcomeActivation.isSelected());
            if (rdbOutcomeActivation.isSelected()) {
                cmbCategory.setItems(FXCollections.observableList(signature.getCategoryOutcomes().stream().map(values -> IncomeOutcomeCategories.builder().id(values.getIdCategoryOutcome()).value(values.getCategoryOutcome()).build()).toList()));
                lblTypeAction.setText("Outcomes");
            } else {
                cmbCategory.setItems(FXCollections.observableList(signature.getTypeIncomes().stream().map(values -> IncomeOutcomeCategories.builder().id(values.getIdTypeIncome()).value(values.getTypeIncome()).build()).collect(Collectors.toList())));
                lblTypeAction.setText("Incomes");
            }
        });
        lblIsWallet.setOnAction(actionEvent -> {
            middlePoint = middlePoint == null ? selectedItem : middlePoint;
            if (lblIsWallet.isSelected()) {
                lblAmountDischarge.setText(signature.getWalletsInstance().getBalanceWallet().toString() + " $");
                selectedItem = null;
            } else {
                lblAmountDischarge.setText(middlePoint == null ? "0.00 $" : middlePoint.getBudgetAssigned().toString() + " $");
                selectedItem = middlePoint;
            }
        });
        TreeItem<String> rootItem = new TreeItem<>("Wallet Organizations");
        walletOrg.forEach(value -> {
            rootItem.getChildren().add(buildTreeContent(value));
        });
        txtAmount.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtAmount.setText(oldValue);
            }
        });
        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);
        rootItem.setExpanded(true);
        rootItem.getChildren().forEach(values -> values.setExpanded(true));
        treeView.setOnMouseReleased(event -> {
            TreeItem<String> selectedItemTree = treeView.getSelectionModel().getSelectedItem();
            if (selectedItemTree == null)
                return;
            final String selectedValue = selectedItemTree.getValue();
            AtomicInteger id = new AtomicInteger();
            origins.getWalletBalances().forEach((key, value) -> {
                if (key.equalsIgnoreCase(selectedValue))
                    id.set(value);
            });
            selectedItem = walletOrg.stream().filter(value -> value.getIdWalletOrganization().equals(id.get())).findFirst().orElse(null);
            lblAmountDischarge.setText(selectedItem != null ? selectedItem.getBudgetAssigned().toString() + " $" : "No Data");
        });
        btnSave.setOnAction(actionEvent -> {
            final boolean isOutcome = rdbOutcomeActivation.isSelected();
            final WalletOrganizations currentPoint = lblIsWallet.isSelected() ? null : selectedItem;
            Outcomes out = isOutcome ? buildFormOutcome() : null;
            Incomes in = isOutcome ? null : buildFormIncome();
            inOutLogic.performTransactions(isOutcome, out, in, currentPoint);
            walletOrg.forEach(value -> rootItem.getChildren().clear());
            budgetLogic.findAll().forEach(value -> rootItem.getChildren().add(buildTreeContent(value)));
            treeView.setRoot(rootItem);
            treeView.setShowRoot(true);
            rootItem.setExpanded(true);
            rootItem.getChildren().forEach(values -> values.setExpanded(true));
            resetForm();
        });
    }

    private TreeItem<String> buildTreeContent(WalletOrganizations org) {
        final TreeItem<String> item = new TreeItem<>(org.getOrganizationName() + " - " + org.getIdWalletOrganization());
        final String content = "Budget Assigned: " + org.getBudgetAssigned() + "$ \n Percentage From Wallet: " + org.getPercentageFromWallet() + "%";
        item.getChildren().add(new TreeItem<>(content));
        origins.setBalances(content, org.getIdWalletOrganization());
        return item;
    }

    private Incomes buildFormIncome() {
        Integer selectedCategory = cmbCategory.getSelectionModel().getSelectedIndex() != -1 ? cmbCategory.getItems().get(cmbCategory.getSelectionModel().getSelectedIndex()).getId() : 0;
        return Incomes.builder()
                .amountIncome(new BigDecimal(txtAmount.getText()))
                .dateIncome(LocalDateTime.now())
                .motiveMovement(txaMotive.getText())
                .typeIncome(selectedCategory)
                .walletId(signature.getWalletsInstance().getIdWallet())
                .build();
    }

    private Outcomes buildFormOutcome() {
        Integer selectedCategory = cmbCategory.getSelectionModel().getSelectedIndex() != -1 ? cmbCategory.getItems().get(cmbCategory.getSelectionModel().getSelectedIndex()).getId() : 0;
        return Outcomes.builder()
                .OutcomeAmount(new BigDecimal(txtAmount.getText()))
                .dateOutcome(LocalDateTime.now())
                .motiveMovement(txaMotive.getText())
                .idCategoryOutcome(selectedCategory)
                .idWallet(signature.getWalletsInstance().getIdWallet())
                .build();
    }

    private void resetForm() {
        txaMotive.setText("");
        txtAmount.setText("");
        lblAmountDischarge.setText("0.00 $");
    }

}
