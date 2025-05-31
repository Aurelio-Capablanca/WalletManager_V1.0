package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.WalletDuration;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.List;

public class WalletDurationRepository extends GenericRepository<WalletDuration, Integer> {

    public WalletDurationRepository() {
        super(WalletDuration.class);
    }

    private final Connector connector = Connector.getInstance();

    public List<WalletDuration> findAllWalletDurations() {
        final String sql = "exec getAllDurations";
        return connector.getSession().createNativeQuery(sql, WalletDuration.class).getResultList();
    }

}
