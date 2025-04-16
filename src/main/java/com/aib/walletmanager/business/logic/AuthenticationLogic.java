package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class AuthenticationLogic {

    private final UserPersistence userPersistence = new UserPersistence();
    private final BcryptFunction encryption = BcryptFunction.getInstance(Bcrypt.B, 12);

    public void authenticateUser(String email, String password) {
        final String encrypted = userPersistence.getUserPassword(email);
        if (encrypted.isEmpty())
            System.out.println("No user found");
        final boolean verification = Password.check(password, encrypted).with(encryption);
        if(verification) System.out.println("Pass");
        else System.out.println("Not pass");
    }

}
