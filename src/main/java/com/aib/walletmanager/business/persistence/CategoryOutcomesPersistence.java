package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.CategoryOutcomes;
import com.aib.walletmanager.repository.CategoryOutcomesRepository;

import java.util.List;

public class CategoryOutcomesPersistence {

    private final CategoryOutcomesRepository repository = new CategoryOutcomesRepository();

    public List<CategoryOutcomes> getAll(){
        return repository.findAllCategories();
    }

}
