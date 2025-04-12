package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.SavesHistorics;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class SavesHistoricsRepository extends GenericRepository<SavesHistorics, Integer> {

    public SavesHistoricsRepository(){
        super(SavesHistorics.class);
    }

}
