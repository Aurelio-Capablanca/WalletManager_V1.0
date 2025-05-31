package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class WalletBudgetsRepository extends GenericRepository<WalletBudgets, Integer> {

    private final Connector connector = Connector.getInstance();

    public WalletBudgetsRepository(){
        super(WalletBudgets.class);
    }

    public List<WalletBudgets> getRelatedToAnOrg(Integer idWallet, Integer idOrganization){
        final String sql = "exec getRelatedToAnOrg  @idOrganizer = :idOrganizer , @idWallet = :idWallet";
        return connector.getSession().createNativeQuery(sql, WalletBudgets.class)
                .setParameter("idWallet",idWallet).setParameter("idOrganizer",idOrganization).getResultList();
    }

    public void saveWalletBudget(WalletBudgets budget, Session session) {
        final Object id = getEntityId(budget);
        final String sql = (id == null)
                ? "exec createBudget @idWallet = :wallet, @idOrganizer = :organizer"
                : "exec UpdateWalletBudget @idWalletBudgets = :id, @idWallet = :wallet, @idOrganizer = :organizer";

        NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class);
        query.setParameter("wallet", budget.getIdWallet());
        query.setParameter("organizer", budget.getIdOrganizer());
        if (id != null) query.setParameter("id", budget.getIdWallet());
        int result = query.executeUpdate();
        if (result == 0) {
            throw new RuntimeException("No rows affected in WalletBudget save.");
        }
    }


    public void deleteWalletBudget(int idWalletBudgets, Session session) {
        NativeQuery<Integer> query = session.createNativeQuery("exec DeleteWalletBudget @idWalletBudgets = :id", Integer.class);
        query.setParameter("id", idWalletBudgets);
        int result = query.executeUpdate();
        if (result == 0) {
            throw new RuntimeException("No WalletBudget deleted (invalid ID?).");
        }
    }


}
