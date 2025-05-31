package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.WalletsRepository;
import org.hibernate.Session;

import java.math.BigDecimal;

public class WalletPersistence {

    private final WalletsRepository repository = new WalletsRepository();

    public Wallets getWalletByUserId(Integer id) {
        return repository.getWalletByUserId(id).orElse(null);
    }

    public void saveWallet(Wallets item, Session session) {
        repository.saveWallets(item, session);
    }

}
