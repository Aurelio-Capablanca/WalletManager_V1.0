package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletBudgetsPersistence;
import com.aib.walletmanager.business.persistence.WalletOrganizationsPersistence;
import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.generics.TransactionWrapper;

import java.util.List;

public class WalletBudgetLogic {

    private final WalletBudgetsPersistence budgetPersistence = new WalletBudgetsPersistence();
    private final WalletOrganizationsPersistence organizationPersistence = new WalletOrganizationsPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();


    public void saveBudgets(WalletOrganizations item) {
        wrapper.executeTransaction(List.of(
                session -> organizationPersistence.saveBudgetUnit(item, session),
                session -> budgetPersistence.saveComposition(WalletBudgets.builder().idOrganizer(item.getIdWalletOrganization()).idWallet(item.getIdWallet()).build(), session)));
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
