package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.repository.UsersRepository;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.hibernate.Session;

import java.util.Optional;

//Service
public class UserPersistence {

    private final UsersRepository repository = new UsersRepository();
    private final BcryptFunction encryption = BcryptFunction.getInstance(Bcrypt.B, 12);

    public Optional<String> getUserPassword(String email) {
        return repository.findUserPassword(email);
    }

    public void saveUsers(Users value, Session session) {
        if (value.getIdUser() == null)
            value.setPassUser(Password.hash(value.getPassUser()).with(encryption).getResult());
        repository.saveUsers(value, session);
    }

    public Users getUserByEmail(String email) {
        return repository.getUsersByEmail(email);
    }


}
