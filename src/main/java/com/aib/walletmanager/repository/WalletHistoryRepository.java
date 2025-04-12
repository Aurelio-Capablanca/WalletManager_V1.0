package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class WalletHistoryRepository extends GenericRepository<WalletHistory, Integer> {

    public WalletHistoryRepository(){
        super(WalletHistory.class);
    }

}
