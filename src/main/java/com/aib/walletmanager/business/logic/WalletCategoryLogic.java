package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletCategoryPersistence;
import com.aib.walletmanager.model.entities.WalletCategories;

import java.util.List;

public class WalletCategoryLogic {

    private final WalletCategoryPersistence walletCategoryPersistence = new WalletCategoryPersistence();

    public List<WalletCategories> getAll(){
        return walletCategoryPersistence.getAll();
    }

}
