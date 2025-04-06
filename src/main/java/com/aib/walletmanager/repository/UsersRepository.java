package com.aib.walletmanager.repository;

import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class UsersRepository extends GenericRepository<Users, Integer> {


    public UsersRepository() {
        super(Users.class);
    }
}
