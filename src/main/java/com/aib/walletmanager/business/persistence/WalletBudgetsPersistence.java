package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.WalletBudgetsRepository;
import org.hibernate.Session;

import java.util.List;

public class WalletBudgetsPersistence {

    private final WalletBudgetsRepository repository = new WalletBudgetsRepository();

    public void saveComposition(WalletBudgets items, Session session){
        repository.saveWithoutTransaction(items, session);
    }

}
