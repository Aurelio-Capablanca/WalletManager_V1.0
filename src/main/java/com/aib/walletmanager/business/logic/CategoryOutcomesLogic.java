package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.model.entities.CategoryOutcomes;
import com.aib.walletmanager.repository.CategoryOutcomesRepository;

import java.util.List;

public class CategoryOutcomesLogic {

    private final CategoryOutcomesRepository categoryOutcomesRepository = new CategoryOutcomesRepository();

    public List<CategoryOutcomes> getAll(){
        return categoryOutcomesRepository.findAllCategories();
    }

}
