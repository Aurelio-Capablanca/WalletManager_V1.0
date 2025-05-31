package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.WalletCategories;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.List;

public class WalletCategoriesRepository extends GenericRepository<WalletCategories, Integer> {

    public WalletCategoriesRepository(){
        super(WalletCategories.class);
    }
    private final Connector connector = Connector.getInstance();

    public List<WalletCategories> getAllCategories() {
        final String sql = "EXECUTE getAllWalletCategories";
        return connector.getSession().createNativeQuery(sql, WalletCategories.class).getResultList();
    }

}
