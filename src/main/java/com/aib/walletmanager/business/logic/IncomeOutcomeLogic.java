package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.IncomePersistence;
import com.aib.walletmanager.business.persistence.OutcomePersistence;
import com.aib.walletmanager.business.persistence.WalletHistoryPersistence;
import com.aib.walletmanager.business.persistence.WalletPersistence;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.TransactionWrapper;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class IncomeOutcomeLogic {


    private final WalletPersistence walletPersistence = new WalletPersistence();
    private final IncomePersistence incomePersistence = new IncomePersistence();
    private final OutcomePersistence outcomePersistence = new OutcomePersistence();
    private final WalletHistoryPersistence historyPersistence = new WalletHistoryPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);

    public void performTransactions(boolean isOutcome, Outcomes out, Incomes in) {
        final Consumer<Session> transaction = isOutcome ? session -> outcomePersistence.saveUnit(out, session) :
                session -> incomePersistence.saveUnit(in, session);
        final Wallets wallets = signature.getWalletsInstance();
        final BigDecimal previousBalance = wallets.getBalanceWallet();
        wallets.setBalanceWallet(isOutcome ? wallets.getBalanceWallet().subtract(out.getOutcomeAmount()) : wallets.getBalanceWallet().add(in.getAmountIncome()));
        final Consumer<Session> saveWallet = session -> walletPersistence.saveWallet(wallets, session);
        final WalletHistory historic = WalletHistory.builder()
                .balanceWallet(wallets.getBalanceWallet())
                .previousBalanceWallet(previousBalance)
                .amountOutcome(isOutcome ? out.getOutcomeAmount() : new BigDecimal(0))
                .amountIncome(isOutcome ? new BigDecimal(0) : in.getAmountIncome())
                .dateSpent(LocalDateTime.now())
                .idWallet(wallets.getIdWallet())
                .build();
        wrapper.executeTransaction(List.of(transaction, saveWallet, session -> historyPersistence.saveHistory(historic, session)));
    }

}
