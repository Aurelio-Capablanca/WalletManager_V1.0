package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class OutcomesRepository extends GenericRepository<Outcomes, Integer> {

    public OutcomesRepository(){
        super(Outcomes.class);
    }


}
