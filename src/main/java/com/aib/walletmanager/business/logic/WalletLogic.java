package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletPersistence;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.model.entities.Wallets;

import java.util.List;

public class WalletLogic {

    private final WalletPersistence walletPersistence = new WalletPersistence();

    public Wallets getWalletByUserId(Integer idUser) {
        return walletPersistence.getWalletByUserId(idUser);
    }



}
