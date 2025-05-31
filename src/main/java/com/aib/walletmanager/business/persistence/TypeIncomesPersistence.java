package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.TypeIncomes;
import com.aib.walletmanager.repository.TypeIncomesRepository;

import java.util.List;

public class TypeIncomesPersistence {

    private final TypeIncomesRepository repository = new TypeIncomesRepository();

    public List<TypeIncomes> getAll(){
        return repository.findAllTypeIncomes();
    }
}
