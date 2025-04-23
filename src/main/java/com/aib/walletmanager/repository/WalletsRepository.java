package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;

import java.math.BigDecimal;

public class WalletsRepository extends GenericRepository<Wallets, Integer> {

    private final Connector connector = Connector.getInstance();
    private final UserSessionSignature sessionSignature = UserSessionSignature.getInstance(null);

    public WalletsRepository() {
        super(Wallets.class);
    }

    public void updateBalanceByTransaction(BigDecimal amount, Integer idWallet, boolean isOutcome, Session session) {
        final String sql = isOutcome ? "update Wallet set balanceWallet = balanceWallet - :amount where idWallet = :idWallet" :
                "update Wallet set balanceWallet = balanceWallet + :amount where idWallet = :idWallet";
        final int action = session.createNativeQuery(sql, Integer.class)
                .setParameter("amount", amount)
                .setParameter("idWallet", idWallet)
                .executeUpdate();
        if (action == 0)
            throw new RuntimeException();
        else {
            connector.getSession().flush();
            connector.getSession().refresh(sessionSignature.getWalletsInstance());
        }
    }

}
