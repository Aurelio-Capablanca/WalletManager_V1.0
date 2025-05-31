package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.repository.OutcomesRepository;
import org.hibernate.Session;

public class OutcomePersistence {

    private final OutcomesRepository repository = new OutcomesRepository();

    public void saveUnit(Outcomes item, Session session) {
        repository.saveOutcome(item, session);
    }

}
