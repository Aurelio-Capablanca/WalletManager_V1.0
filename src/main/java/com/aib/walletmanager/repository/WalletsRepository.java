package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletsRepository extends GenericRepository<Wallets, Integer> {

    public WalletsRepository(){
        super(Wallets.class);
    }


}
