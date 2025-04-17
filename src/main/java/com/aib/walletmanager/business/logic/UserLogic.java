package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.model.entities.Users;

public class UserLogic {

    private final UserPersistence userPersistence = new UserPersistence();

    public Users findByEmail(String email){
        return userPersistence.getUserByEmail(email);
    }

}
