package com.aib.walletmanager.model.dataHolders;

import com.aib.walletmanager.business.logic.UserLogic;
import com.aib.walletmanager.business.logic.WalletLogic;
import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.Wallets;
import lombok.Getter;


public class UserSessionSignature {

    private static volatile UserSessionSignature instance;
    @Getter
    private Users usersInstance;
    @Getter
    private Wallets walletsInstance;

    private final UserLogic userLogic = new UserLogic();
    private final WalletLogic walletLogic = new WalletLogic();

    private UserSessionSignature(String emailUser) {
        if (emailUser == null)
            return;
        setInstances(emailUser);
    }

    public static UserSessionSignature getInstance(String emailUser) {
        UserSessionSignature result = instance;
        if (result != null)
            return result;
        synchronized (UserSessionSignature.class) {
            if (instance == null && emailUser != null)
                instance = new UserSessionSignature(emailUser);
        }
        return instance;
    }


    private void setInstances(String email) {
        usersInstance = userLogic.findByEmail(email);
        walletsInstance = walletLogic.getWalletByUserId(usersInstance.getIdUser());
        System.out.println("Users : " + usersInstance + " Wallet: " + walletsInstance);
    }

}
