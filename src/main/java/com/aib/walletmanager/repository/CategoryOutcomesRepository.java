package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.CategoryOutcomes;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class CategoryOutcomesRepository extends GenericRepository<CategoryOutcomes, Integer> {

    public CategoryOutcomesRepository(){
        super(CategoryOutcomes.class);
    }

}
