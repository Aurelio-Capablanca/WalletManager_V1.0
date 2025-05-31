package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletOrganizations;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.Objects;

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

    public void deleteWalletOrganization(int idOrganization, Session session){
        NativeQuery<Integer> query = session.createNativeQuery("exec deleteUnitWalletOrganizations @idOrganizer = :id", Integer.class);
        query.setParameter("id", idOrganization);
        int result = query.executeUpdate();
        if (result == 0) {
            throw new RuntimeException("No WalletBudget deleted (invalid ID?).");
        }
    }

    public void saveWalletOrganization(WalletOrganizations item, Session session){
        final Object id = getEntityId(item);
        final String sql = Objects.isNull(id) ?
                "exec InsertWalletOrganization @OrganizerName = :OrganizerName , @budgetAsigned = :budgetAsigned, @percentageFromWallet = :percentageFromWallet , @creationOrganization = :creationOrganization , @idWalletCategory = :idWalletCategory , @idWallet = :idWallet , @endPeriod = :endPeriod , @idDuration = :idDuratio , @startPeriod = :startPeriod"
                : "exec UpdateWalletOrganization @OrganizerName = :OrganizerName , @budgetAsigned = :budgetAsigned, @percentageFromWallet = :percentageFromWallet , @creationOrganization = :creationOrganization , @idWalletCategory = :idWalletCategory , @idWallet = :idWallet , @endPeriod = :endPeriod , @idDuration = :idDuratio , @startPeriod = :startPeriod , @idOrganizer = :idOrganizer";
        NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
        nativeQuery.setParameter("OrganizerName",item.getOrganizationName())
                .setParameter("budgetAsigned", item.getBudgetAssigned())
                .setParameter("percentageFromWallet", item.getPercentageFromWallet())
                .setParameter("creationOrganization",item.getCreationOrganization())
                .setParameter("idWalletCategory",item.getIdWalletCategory())
                .setParameter("idWallet", item.getIdWallet())
                .setParameter("endPeriod",item.getEndDuration())
                .setParameter("idDuratio",item.getIdTimeSet())
                .setParameter("startPeriod", item.getStartDuration());
        if (Objects.nonNull(id)) nativeQuery.setParameter("idOrganizer", item.getIdWalletOrganization());
        final int action = nativeQuery.getSingleResult();
        item.setIdWalletOrganization(action);
        if (action == 0) throw new RuntimeException();
        session.flush();
    }


}
