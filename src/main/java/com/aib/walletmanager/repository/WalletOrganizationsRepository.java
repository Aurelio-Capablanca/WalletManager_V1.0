package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.List;

public class WalletOrganizationsRepository extends GenericRepository<WalletOrganizations, Integer> {

    public WalletOrganizationsRepository(){
        super(WalletOrganizations.class);
    }

    private final Connector connector = Connector.getInstance();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);

    public List<WalletOrganizations> getAllOrgByWallet(){
        final String sql = "exec getAllWalletOrganizationsByWallet :idWallet";
        return connector.getSession().createNativeQuery(sql, WalletOrganizations.class)
                .setParameter("idWallet", signature.getWalletsInstance().getIdWallet()).getResultList();
    }



}
