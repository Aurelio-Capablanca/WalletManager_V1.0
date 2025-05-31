package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.model.entities.TypeIncomes;
import com.aib.walletmanager.repository.TypeIncomesRepository;

import java.util.List;

public class TypeIncomesLogic {

    private final TypeIncomesRepository typeIncomesRepository = new TypeIncomesRepository();

    public List<TypeIncomes> getAll(){
        return typeIncomesRepository.findAllTypeIncomes();
    }

}
