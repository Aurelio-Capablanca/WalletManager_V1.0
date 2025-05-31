package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletBudgetsPersistence;
import com.aib.walletmanager.business.persistence.WalletOrganizationsPersistence;
import com.aib.walletmanager.business.rules.AlertFactory.AlertResponses;
import com.aib.walletmanager.business.rules.AlertFactory.AlertTypes;
import com.aib.walletmanager.business.rules.businessRules.BudgetRules;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.generics.TransactionWrapper;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.Optional;

public class WalletBudgetLogic {

    private final WalletBudgetsPersistence budgetPersistence = new WalletBudgetsPersistence();
    private final WalletOrganizationsPersistence organizationPersistence = new WalletOrganizationsPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();
    private final BudgetRules rules = new BudgetRules();


    public void saveBudgets(WalletOrganizations item) {
        Optional<ResponseValidator> response = rules.validateBudgets(item);
        if (response.isPresent()) {
            Alert alert = AlertResponses.alertResponses(AlertTypes.ERROR, "Error !", "Error at Budget", response.get().getMessage());
            alert.show();
            return;
        }
        wrapper.executeTransaction(List.of(
                session -> organizationPersistence.saveBudgetUnit(item, session),
                session -> budgetPersistence.saveComposition(WalletBudgets.builder().idOrganizer(item.getIdWalletOrganization()).idWallet(item.getIdWallet()).build(), session)));
        Alert success = AlertResponses.alertResponses(AlertTypes.INFORMATION, "Success", "", "Budget saved!!");
        success.show();
    }

    public List<WalletOrganizations> findAll() {
        return organizationPersistence.getAll();
    }

    public void deleteEntities(WalletOrganizations item) {
        List<WalletBudgets> budgets = budgetPersistence.getRelatedToAnOrg(item.getIdWallet(), item.getIdWalletOrganization());
        if (!budgets.isEmpty()) {
            wrapper.executeTransaction(List.of(
                    session -> budgetPersistence.deleteAll(budgets, session),
                    session -> organizationPersistence.deleteUnit(item, session)));
        }
    }

}
