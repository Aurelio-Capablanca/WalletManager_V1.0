package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.repository.IncomesRepository;
import org.hibernate.Session;

public class IncomePersistence {

    private final IncomesRepository repository = new IncomesRepository();

    public void saveUnit(Incomes item, Session session){
        repository.saveIncome(item, session);
    }

}
