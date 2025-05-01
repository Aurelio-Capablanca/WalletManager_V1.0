package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletHistoryPersistence;
import com.aib.walletmanager.model.entities.WalletHistory;

import java.time.LocalDate;
import java.util.List;


public class WalletHistoryLogic {

    private final WalletHistoryPersistence historyPersistence = new WalletHistoryPersistence();

    public List<WalletHistory> searchHistoric(LocalDate from, LocalDate to) {
        return historyPersistence.searchHistoric(from, to);
    }


}
