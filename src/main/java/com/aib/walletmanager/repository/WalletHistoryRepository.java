package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class WalletHistoryRepository extends GenericRepository<WalletHistory, Integer> {

    private final Connector connector = Connector.getInstance();

    public WalletHistoryRepository() {
        super(WalletHistory.class);
    }

    public final List<WalletHistory> searchByDates(LocalDate from, LocalDate to) {
        final String sql = "exec searchHistoryByDate @from = :from, @to = :to";
        return connector.getSession().createNativeQuery(sql, WalletHistory.class)
                .setParameter("from", from).setParameter("to", to).list();
    }

    public void saveHistoric(WalletHistory item, Session session) {
        final Object id = getEntityId(item);
        final String sql = Objects.isNull(id) ?
                "exec InsertWalletHistory @balanceWallet = :balanceWallet, @amountOutcome = :amountOutcome, @amountIncome = :amountIncome, @dateSpent = :dateSpent, @idWallet = :idWallet, @previousBalance = :previousBalance"
                : "exec UpdateWalletHistory @balanceWallet = :balanceWallet, @amountOutcome = :amountOutcome, @amountIncome = :amountIncome, @dateSpent = :dateSpent, @idWallet = :idWallet, @previousBalance = :previousBalance, @idHistoryWallet = :idHistoryWallet";
        NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
        nativeQuery.setParameter("balanceWallet", item.getBalanceWallet())
                .setParameter("amountOutcome", item.getAmountOutcome())
                .setParameter("amountIncome", item.getAmountIncome())
                .setParameter("dateSpent", item.getDateSpent())
                .setParameter("idWallet", item.getIdWallet())
                .setParameter("previousBalance", item.getPreviousBalanceWallet());
        if (Objects.nonNull(id)) nativeQuery.setParameter("idHistoryWallet", item.getIdWalletHistory());
        final int action = nativeQuery.getSingleResult();
        item.setIdWalletHistory(action);
        if (action == 0) throw new RuntimeException();
        session.flush();
    }

}
