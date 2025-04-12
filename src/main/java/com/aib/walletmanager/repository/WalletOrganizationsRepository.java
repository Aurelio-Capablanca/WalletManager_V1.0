package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletOrganizationsRepository extends GenericRepository<WalletOrganizations, Integer> {

    public WalletOrganizationsRepository(){
        super(WalletOrganizations.class);
    }

}
