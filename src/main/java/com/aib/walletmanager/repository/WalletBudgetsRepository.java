package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletBudgetsRepository extends GenericRepository<WalletBudgets, Integer> {

    public WalletBudgetsRepository(){
        super(WalletBudgets.class);
    }

}
