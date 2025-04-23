package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.IncomeOutcomeLogic;
import com.aib.walletmanager.business.logic.WalletBudgetLogic;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.DTO.IncomeOutcomeCategories;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);
    private final WalletBudgetLogic budgetLogic = new WalletBudgetLogic();
    private final IncomeOutcomeLogic inOutLogic = new IncomeOutcomeLogic();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            System.out.println("Is Selected :"+rdbOutcomeActivation.isSelected());
            if(rdbOutcomeActivation.isSelected()) {
                cmbCategory.setItems(FXCollections.observableList(signature.getCategoryOutcomes().stream().map(values -> IncomeOutcomeCategories.builder().id(values.getIdCategoryOutcome()).value(values.getCategoryOutcome()).build()).toList()));
                lblTypeAction.setText("Outcomes");
            }
            else {
                cmbCategory.setItems(FXCollections.observableList(signature.getTypeIncomes().stream().map(values -> IncomeOutcomeCategories.builder().id(values.getIdTypeIncome()).value(values.getTypeIncome()).build()).collect(Collectors.toList())));
                lblTypeAction.setText("Incomes");
            }
        });
        final List<WalletOrganizations> walletOrg = budgetLogic.findAll();
        TreeItem<String> rootItem = new TreeItem<>("Wallet Organizations");
        walletOrg.forEach(value -> {
            rootItem.getChildren().add(buildTreeContent(value));
        });
        treeView.setRoot(rootItem);
        treeView.setShowRoot(true);
        rootItem.setExpanded(true);
        rootItem.getChildren().forEach(values -> values.setExpanded(true));
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, newVal, oldVal) -> {
            if (newVal != null) {
                // Get the selected item's value
                String selectedValue = newVal.getValue();
                System.out.println("Selected Value: " + selectedValue);

                // Get the children of the selected item
                newVal.getChildren().forEach(child -> {
                    System.out.println("Child: " + child.getValue());
                });
            }
        });
    }

    private TreeItem<String> buildTreeContent(WalletOrganizations org){
        final TreeItem<String> item = new TreeItem<>(org.getOrganizationName());
        item.getChildren().add(new TreeItem<>("Budget Assigned: " + org.getBudgetAssigned()+"$ - Percentage From Wallet: " + org.getPercentageFromWallet()+"%"));
        item.getChildren().add(new TreeItem<>("Start Period: " + org.getStartDuration()+"- End Period: " + (org.getEndDuration() == null ? "" : org.getEndDuration())));
        return item;
    }
}
