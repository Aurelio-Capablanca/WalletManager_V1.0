package com.aib.walletmanager.repository.generics;


import com.aib.walletmanager.connectorFactory.Connector;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class TransactionWrapper {

    private final Connector connector = Connector.getInstance();

    public <T> void executeTransaction(List<Consumer<Session>> transactions) {
        Transaction current = null;
        try (Session session = connector.getMainSession().openSession()) {
            current = session.beginTransaction();
            transactions.forEach(transaction -> {
                if (transaction == null) {
                    System.out.println("No Transaction to Submit here, bypassing!");
                    return;
                }
                try {
                    transaction.accept(session);
                } catch (Exception e) {
                    System.err.println("Error during transaction lambda execution: " + e.getMessage());
                    throw e;
                }
            });
            current.commit();
        } catch (Exception e) {
            if (current != null && current.getStatus().canRollback()) {
                try {
                    current.rollback();
                } catch (Exception rollbackError) {
                    System.err.println("Rollback failed: " + rollbackError.getMessage());
                }
            }
            throw new RuntimeException(e);
        }
    }

}
