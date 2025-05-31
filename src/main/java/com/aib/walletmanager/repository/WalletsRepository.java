package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.CategoryOutcomes;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.WalletHistory;
import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class WalletsRepository extends GenericRepository<Wallets, Integer> {

    private final Connector connector = Connector.getInstance();
    private final UserSessionSignature sessionSignature = UserSessionSignature.getInstance(null);

    public WalletsRepository() {
        super(Wallets.class);
    }

    public Optional<Wallets> getWalletByUserId(Integer idUser){
        final String sql = "exec walletByUserID @idUser = :id";
        return Optional.of(connector.getSession().createNativeQuery(sql, Wallets.class)
                .setParameter("id", idUser).getSingleResult());
    }

    public void saveWallets(Wallets item, Session session) {
        final Object id = getEntityId(item);
        final String sql = Objects.isNull(id) ?
                "exec InsertWallet @codeWallet = :codeWallet, @balanceWallet = :balanceWallet, @idUser = :idUser"
                : "exec UpdateWallet @codeWallet = :codeWallet, @balanceWallet = :balanceWallet, @idUser = :idUser, @idWallet = :idWallet";
        NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
        nativeQuery.setParameter("codeWallet", item.getCodeWallet())
                .setParameter("balanceWallet", item.getBalanceWallet())
                .setParameter("idUser", item.getIdUser());
        if (Objects.nonNull(id)) nativeQuery.setParameter("idWallet", item.getIdWallet());
        final int action = nativeQuery.getSingleResult();
        item.setIdWallet(action);
        if (action == 0) throw new RuntimeException();
        session.flush();
    }

}
