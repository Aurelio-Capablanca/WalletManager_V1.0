package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class IncomesRepository extends GenericRepository<Incomes, Integer> {

    public IncomesRepository(){
        super(Incomes.class);
    }

}
