package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.CategoryOutcomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

import java.util.List;

public class CategoryOutcomesRepository extends GenericRepository<CategoryOutcomes, Integer> {

    public CategoryOutcomesRepository(){
        super(CategoryOutcomes.class);
    }

    private final Connector connector = Connector.getInstance();

    public List<CategoryOutcomes> findAllCategories(){
        final String sql = "exec getAllCategoryOutcomes";
        return connector.getSession().createNativeQuery(sql, CategoryOutcomes.class).getResultList();
    }
}
