package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletHistoryPersistence;
import com.aib.walletmanager.business.rules.AlertFactory.AlertResponses;
import com.aib.walletmanager.business.rules.AlertFactory.AlertTypes;
import com.aib.walletmanager.business.rules.businessRules.HistoryRules;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.entities.WalletHistory;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class WalletHistoryLogic {

    private final WalletHistoryPersistence historyPersistence = new WalletHistoryPersistence();
    private final HistoryRules rules = new HistoryRules();


    public List<WalletHistory> searchHistoric(LocalDate from, LocalDate to) {
//        final Optional<ResponseValidator> response = rules.validateHistoric(from, to);
//        if(response.isPresent()){
//            Alert alert =  AlertResponses.alertResponses(AlertTypes.ERROR, "Error !", "Error at Historic Transactions", response.get().getMessage());
//            alert.show();
//            return Collections.emptyList();
//        }
        return historyPersistence.searchHistoric(from, to);
    }


}
