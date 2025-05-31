package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.*;
import com.aib.walletmanager.business.rules.AlertFactory.AlertResponses;
import com.aib.walletmanager.business.rules.AlertFactory.AlertTypes;
import com.aib.walletmanager.business.rules.businessRules.IncomeOutcomeRules;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.*;
import com.aib.walletmanager.repository.generics.TransactionWrapper;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class IncomeOutcomeLogic {


    private final WalletPersistence walletPersistence = new WalletPersistence();
    private final IncomePersistence incomePersistence = new IncomePersistence();
    private final OutcomePersistence outcomePersistence = new OutcomePersistence();
    private final WalletOrganizationsPersistence orgPersistence = new WalletOrganizationsPersistence();
    private final WalletHistoryPersistence historyPersistence = new WalletHistoryPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);
    private final IncomeOutcomeRules rules = new IncomeOutcomeRules();

    public void performTransactions(boolean isOutcome, Outcomes out, Incomes in, WalletOrganizations org) {
        final Optional<ResponseValidator> response = rules.validateIncomeOutcomeLogic(out, in, org);
        if (response.isPresent()){
            Alert alert =  AlertResponses.alertResponses(AlertTypes.ERROR, "Error !", "Error at Input/Output", response.get().getMessage());
            alert.show();
            return;
        }
        //validations
        final Consumer<Session> transaction = isOutcome ? session -> outcomePersistence.saveUnit(out, session) :
                session -> incomePersistence.saveUnit(in, session);
        final Wallets wallets = signature.getWalletsInstance();
        final BigDecimal previousBalance = wallets.getBalanceWallet();
        Consumer<Session> updateForOrganization = null;
        BigDecimal walletNewEstimated = new BigDecimal(0);
        if (org != null) {
            org.setBudgetAssigned(isOutcome ? org.getBudgetAssigned().subtract(out.getOutcomeAmount()) : org.getBudgetAssigned().add(in.getAmountIncome()));
            walletNewEstimated = isOutcome ? wallets.getBalanceWallet().subtract(org.getBudgetAssigned()) : wallets.getBalanceWallet().add(org.getBudgetAssigned());
            org.setPercentageFromWallet(Double.valueOf(org.getBudgetAssigned().divide(walletNewEstimated, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).toPlainString()));
            updateForOrganization = session -> orgPersistence.saveBudgetUnit(org, session);
        }
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
        if (org != null)
            wrapper.executeTransaction(List.of(transaction, saveWallet, updateForOrganization, session -> historyPersistence.saveHistory(historic, session)));
        else
            wrapper.executeTransaction(List.of(transaction, saveWallet, session -> historyPersistence.saveHistory(historic, session)));
        Alert success = AlertResponses.alertResponses(AlertTypes.INFORMATION, "Success", "", "Transaction made successfully");
        success.show();
    }

}
