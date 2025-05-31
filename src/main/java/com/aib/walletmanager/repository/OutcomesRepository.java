package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public class OutcomesRepository extends GenericRepository<Outcomes, Integer> {

    public OutcomesRepository() {
        super(Outcomes.class);
    }

    public void saveOutcome(Outcomes outcome, Session session) {
        final Object id = getEntityId(outcome);
        final String sql = (id == null)
                ? "exec InsertOutcome @idCategoryOutcome = :category, @OutcomeAmount = :amount, @idWallet = :wallet, @Motive = :motive, @dateOutcome = :date"
                : "exec UpdateOutcome @idOutcome = :id, @idCategoryOutcome = :category, @OutcomeAmount = :amount, @idWallet = :wallet, @Motive = :motive, @dateOutcome = :date";
        NativeQuery<Integer> query = session.createNativeQuery(sql, Integer.class);
        query.setParameter("category", outcome.getIdCategoryOutcome());
        query.setParameter("amount", outcome.getOutcomeAmount());
        query.setParameter("wallet", outcome.getIdWallet());
        query.setParameter("motive", outcome.getMotiveMovement());
        query.setParameter("date", outcome.getDateOutcome());
        if (id != null) query.setParameter("id", outcome.getIdOutcome());
        int result = query.executeUpdate();
        if (result == 0) throw new RuntimeException("No rows affected in outcome save.");
    }


}
