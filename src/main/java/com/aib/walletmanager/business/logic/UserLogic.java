package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.business.persistence.WalletPersistence;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.TransactionWrapper;

import java.math.BigDecimal;
import java.util.List;

public class UserLogic {

    private final UserPersistence userPersistence = new UserPersistence();
    private final WalletPersistence walletPersistence = new WalletPersistence();
    private final TransactionWrapper wrapper = new TransactionWrapper();

    public Users findByEmail(String email){
        return userPersistence.getUserByEmail(email);
    }

    public void saveUser(Users user){
        wrapper.executeTransaction(List.of(session -> userPersistence.saveUsers(user, session),
                session -> walletPersistence.saveWallet(Wallets.builder().codeWallet("code")
                                .idUser(user.getIdUser())
                                .balanceWallet(BigDecimal.ZERO)
                        .build(), session)
        ));
    }

}
