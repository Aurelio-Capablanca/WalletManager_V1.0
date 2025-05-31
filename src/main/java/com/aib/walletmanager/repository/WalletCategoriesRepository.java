package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletCategoriesRepository extends GenericRepository<WalletCategories, Integer> {

    public WalletCategoriesRepository(){
        super(WalletCategories.class);
    }



}
