package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.repository.WalletCategoriesRepository;

import java.util.List;

public class WalletCategoryPersistence {

    private final WalletCategoriesRepository repository = new WalletCategoriesRepository();

    public List<WalletCategories> getAll(){
        return repository.getAllCategories();
    }


}
