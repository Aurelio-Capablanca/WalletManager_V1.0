package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.TypeIncomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class TypeIncomesRepository extends GenericRepository<TypeIncomes, Integer> {

    public TypeIncomesRepository(){
        super(TypeIncomes.class);
    }

}
