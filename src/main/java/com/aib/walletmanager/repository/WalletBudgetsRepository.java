package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.ArrayList;
import java.util.List;

public class WalletBudgetsRepository extends GenericRepository<WalletBudgets, Integer> {

    private final Connector connector = Connector.getInstance();

    public WalletBudgetsRepository(){
        super(WalletBudgets.class);
    }

    public List<WalletBudgets> getRelatedToAnOrg(Integer idWallet, Integer idOrganization){
        final String sql = "select * from WalletBudgets wb  where wb.idWallet = :idWallet And wb.idOrganizer = :idOrganizer";
        return connector.getSession().createNativeQuery(sql, WalletBudgets.class)
                .setParameter("idWallet",idWallet).setParameter("idOrganizer",idOrganization).getResultList();
    }

}
