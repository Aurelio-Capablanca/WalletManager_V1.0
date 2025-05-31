package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.business.persistence.WalletPersistence;
import com.aib.walletmanager.business.rules.AlertFactory.AlertResponses;
import com.aib.walletmanager.business.rules.AlertFactory.AlertTypes;
import com.aib.walletmanager.business.rules.businessRules.UserRules;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.TransactionWrapper;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserLogic {

    private final UserPersistence userPersistence = new UserPersistence();
    private final WalletPersistence walletPersistence = new WalletPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();
    private final UserRules rules = new UserRules();

    public Users findByEmail(String email){
        return userPersistence.getUserByEmail(email);
    }

    public void saveUser(Users user){
        final Optional<ResponseValidator> response = rules.validateUsers(user);
        if(response.isPresent()){
            Alert alert =  AlertResponses.alertResponses(AlertTypes.ERROR, "Error !", "Error at Users", response.get().getMessage());
            alert.show();
            return;
        }
        wrapper.executeTransaction(List.of(session -> userPersistence.saveUsers(user, session),
                session -> walletPersistence.saveWallet(Wallets.builder().codeWallet("code")
                                .idUser(user.getIdUser())
                                .balanceWallet(BigDecimal.ZERO)
                        .build(), session)
        ));
        Alert success = AlertResponses.alertResponses(AlertTypes.INFORMATION, "Success", "", "User saved successfully");
        success.show();
    }

}
