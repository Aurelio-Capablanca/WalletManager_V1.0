package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.DurationCategories;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class DurationCategoriesRepository extends GenericRepository<DurationCategories, Integer> {

    public DurationCategoriesRepository(){
        super(DurationCategories.class);
    }

}
