package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.repository.WalletDurationRepository;

import java.util.List;

public class WalletDurationLogic {

    private final WalletDurationRepository walletDurationRepository = new WalletDurationRepository();

    public final List<WalletDuration> getAll(){
        return walletDurationRepository.findAll();
    }

}
