package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.repository.WalletHistoryRepository;
import org.hibernate.Session;

public class WalletHistoryPersistence {

    private final WalletHistoryRepository repository = new WalletHistoryRepository();

    public void saveHistory(WalletHistory item, Session session){
        repository.saveWithoutTransaction(item, session);
    }

}
