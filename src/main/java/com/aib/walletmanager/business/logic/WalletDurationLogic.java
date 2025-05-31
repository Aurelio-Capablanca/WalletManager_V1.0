package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.WalletDurationsPersistence;
import com.aib.walletmanager.model.entities.WalletDuration;

import java.util.List;

public class WalletDurationLogic {

    private final WalletDurationsPersistence walletDurationRepository = new WalletDurationsPersistence();

    public final List<WalletDuration> getAll(){
        return walletDurationRepository.getAll();
    }

}
