package com.aib.walletmanager.repository.generics;


import com.aib.walletmanager.connectorFactory.Connector;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class TransactionWrapper {

    private final Connector connector;

    public <T> void executeTransaction(List<Consumer<Session>> transactions) {
        Transaction current = null;
        try (Session session = connector.getMainSession().openSession()) {
            current = session.beginTransaction();
            transactions.forEach(transaction -> transaction.accept(session));
            current.commit();
        } catch (Exception e) {
            if (current != null)
                current.rollback();
            throw new RuntimeException(e);
        }
    }

}
