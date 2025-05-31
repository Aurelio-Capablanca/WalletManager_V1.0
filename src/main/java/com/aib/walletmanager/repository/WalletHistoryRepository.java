package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.time.LocalDate;
import java.util.List;

public class WalletHistoryRepository extends GenericRepository<WalletHistory, Integer> {

    private final Connector connector = Connector.getInstance();
    public WalletHistoryRepository(){
        super(WalletHistory.class);
    }

    public final List<WalletHistory> searchByDates(LocalDate from, LocalDate to){
        final String sql = "exec searchHistoryByDate @from = :from, @to = :to";
        return connector.getSession().createNativeQuery(sql,WalletHistory.class)
                .setParameter("from",from).setParameter("to",to).list();
    }

}
