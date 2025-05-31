package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.repository.WalletDurationRepository;

import java.util.List;

public class WalletDurationsPersistence {

    private final WalletDurationRepository repository = new WalletDurationRepository();

    public List<WalletDuration> getAll(){
        return repository.findAllWalletDurations();
    }

}
