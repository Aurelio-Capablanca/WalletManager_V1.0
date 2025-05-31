package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public class IncomesRepository extends GenericRepository<Incomes, Integer> {

    public IncomesRepository(){
        super(Incomes.class);
    }

    public void saveIncome(Incomes income, Session session) {
        final Object id = getEntityId(income);
        final String sql = (id == null)
                ? "exec InsertIncome @amountIncome = :amount, @idTypeIncome = :type, @idWallet = :wallet, @Motive = :motive, @dateIncome = :date"
                : "exec UpdateIncome @idIncome = :id, @amountIncome = :amount, @idTypeIncome = :type, @idWallet = :wallet, @Motive = :motive, @dateIncome = :date";
        NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class);
        query.setParameter("amount", income.getAmountIncome());
        query.setParameter("type", income.getTypeIncome());
        query.setParameter("wallet", income.getWalletId());
        query.setParameter("motive", income.getMotiveMovement());
        query.setParameter("date", income.getDateIncome());
        if (id != null) query.setParameter("id", income.getIdIncomes());
        int result = query.executeUpdate();
        if (result == 0) throw new RuntimeException("No rows affected in income save.");
    }


}
