package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.repository.WalletCategoriesRepository;

import java.util.List;

public class WalletCategoryLogic {

    private final WalletCategoriesRepository walletCategoriesRepository = new WalletCategoriesRepository();

    public List<WalletCategories> getAll(){
        return walletCategoriesRepository.findAll();
    }

}
