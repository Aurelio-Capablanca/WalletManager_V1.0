package com.aib.walletmanager.repository;

import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.repository.generics.GenericRepository;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.Objects;

public class UsersRepository extends GenericRepository<Users, Integer> {

    private final Connector connector = Connector.getInstance();

    public UsersRepository() {
        super(Users.class);
    }

    public String findUserPassword(String emailUser) {
        final String sql = "execute findUserPassword :email";
        return connector.getSession().createNativeQuery(sql, String.class)
                .setParameter("email", emailUser).getSingleResult();
    }

    public Users getUsersByEmail(String email) {
        final String sql = "EXECUTE getUserByEmail :email";
        return connector.getSession().createNativeQuery(sql, Users.class)
                .setParameter("email", email).getSingleResult();
    }


    public void saveUsers(Users user, Session session) {
        final Object id = getEntityId(user);
        final String sql = Objects.isNull(id) ?
                "exec insertUser @nameUsers = :name,  @lastnamteUsers = :lastname, @emailUser = :email, @passUser= :password, @statusUser= :status"
                : "exec updateUser @nameUsers = :name,  @lastnamteUsers = :lastname, @emailUser = :email, @passUser= :password, @statusUser= :status, @idUsers = :id";
        NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
        nativeQuery.setParameter("name", user.getNameUser())
                .setParameter("lastname", user.getLastNameUser())
                .setParameter("password", user.getPassUser())
                .setParameter("status", user.getStatusUser());
        if (Objects.nonNull(id)) nativeQuery.setParameter("id", user.getIdUser());
        final int action = nativeQuery.executeUpdate();
        if (action == 0) throw new RuntimeException();
    }
}
