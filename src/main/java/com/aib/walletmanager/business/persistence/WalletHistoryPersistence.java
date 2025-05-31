package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.repository.WalletHistoryRepository;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class WalletHistoryPersistence {

    private final WalletHistoryRepository repository = new WalletHistoryRepository();

    public void saveHistory(WalletHistory item, Session session){
        repository.saveHistoric(item, session);
    }

    public List<WalletHistory> searchHistoric(LocalDate from, LocalDate to){
        return repository.searchByDates(from, to);
    }

}
