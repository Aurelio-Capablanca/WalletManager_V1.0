package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.repository.generics.GenericRepository;

public class UsersRepository extends GenericRepository<Users, Integer> {

    private final Connector connector = Connector.getInstance();

    public UsersRepository() {
        super(Users.class);
    }

    public String findUserPassword(String emailUser){
       final String sql = "select passUser from Users Where emailUser = :email";/*'aurel@mail.com'*/
        return connector.getSession().createNativeQuery(sql, String.class)
                .setParameter("email", emailUser).getSingleResult();
    }



}
