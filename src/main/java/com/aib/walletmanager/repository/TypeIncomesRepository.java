package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.TypeIncomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.List;

public class TypeIncomesRepository extends GenericRepository<TypeIncomes, Integer> {

    public TypeIncomesRepository(){
        super(TypeIncomes.class);
    }

    private final Connector connector = Connector.getInstance();

    public List<TypeIncomes> findAllTypeIncomes(){
        final String sql = "exec getAllTypeIncomes";
        return connector.getSession().createNativeQuery(sql, TypeIncomes.class).getResultList();
    }

}
