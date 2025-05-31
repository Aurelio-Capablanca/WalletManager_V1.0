package com.aib.walletmanager.repository.generics;


import com.aib.walletmanager.connectorFactory.Connector;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@AllArgsConstructor
public class TransactionWrapper {

    private final Connector connector = Connector.getInstance();

    public <T> void executeTransaction(List<Consumer<Session>> transactions) {
        AtomicReference<Transaction> current = new AtomicReference<>();
        try (Session session = connector.getMainSession().openSession()) {
            current.set(session.beginTransaction());
            transactions.forEach(transaction -> {
                if (transaction == null) {
                    System.out.println("No Transaction to Submit here, bypassing!");
                    return;
                }
                try {
                    transaction.accept(session);
                } catch (Exception e) {
                    if (current.get() != null) {
                        current.get().rollback();
                    }
                    System.err.println("Error during transaction lambda execution: " + e.getMessage());
                    throw e;
                }
            });
            current.get().commit();
        } catch (Exception e) {
            if (current.get() != null && current.get().getStatus().canRollback()) {
                try {
                    current.get().rollback();
                } catch (Exception rollbackError) {
                    System.err.println("Rollback failed: " + rollbackError.getMessage());
                }
            }
            throw new RuntimeException(e);
        }
    }

}
