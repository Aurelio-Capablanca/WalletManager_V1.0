package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletDurationRepository extends GenericRepository<WalletDuration, Integer> {

    public WalletDurationRepository() {
        super(WalletDuration.class);
    }

}
