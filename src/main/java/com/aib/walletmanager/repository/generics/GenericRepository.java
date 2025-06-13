package com.aib.walletmanager.repository.generics;

import com.aib.walletmanager.connectorFactory.Connector;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.NativeQuery;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GenericRepository<T, ID> {

    private final Class<T> entity;
    private final Connector sessionConnector = Connector.getInstance();

    public GenericRepository(Class<T> entity) {
        this.entity = entity;
    }

    public final void save(T entity) {
        Transaction transaction = null;
        try (Session session = sessionConnector.getMainSession().openSession()) {
            transaction = session.beginTransaction();
            final Object id = getEntityId(entity);
            if (id == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            transaction.commit();
        } catch (TransactionException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

//    public final void delete(T entity) {
//        Transaction transaction = null;
//        try (Session session = sessionConnector.getMainSession().openSession()) {
//            transaction = session.beginTransaction();
//            final Object id = getEntityId(entity);
//            if (id != null)
//                session.remove(entity);
//            transaction.commit();
//        } catch (TransactionException e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }

    public final void deleteWithoutTransaction(T entity, Session session) {
        final Object id = getEntityId(entity);
        if (id != null)
            session.remove(entity);
    }

    public final void deleteAllWithoutTransaction(Collection<T> entity, Session session) {
        entity.forEach(items -> {
            final Object id = getEntityId(items);
            if (id != null)
                session.remove(items);
        });
    }

    public final void saveWithoutTransaction(T entity, Session session) {
        final Object id = getEntityId(entity);
        if (id == null) {
            session.persist(entity);
        } else {
            session.merge(entity);
        }
    }

    public final void saveAll(Collection<T> collection) {
        Transaction transaction = null;
        try (Session session = sessionConnector.getMainSession().openSession()) {
            transaction = session.beginTransaction();
            int batchSize = 20;
            int countBatches = 0;
            for (T entity : collection) {
                Object id = getEntityId(entity);
                if (id == null) {
                    session.persist(entity);
                } else {
                    session.merge(entity);
                }
                if (++countBatches % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Consider logging instead
        }
    }

    public final List<T> findAll() {
        try (Session session = sessionConnector.getMainSession().openSession()) {
            return session.createQuery("from " + entity.getName(), entity).list();
        }
    }

    public final Optional<T> findByIntegerAttribute(String attributeName, Integer value) {
        try (Session session = sessionConnector.getMainSession().openSession()) {
            T result = session.createQuery("from " + entity.getName() + " where " + attributeName + " =  :" + attributeName, entity).setParameter(attributeName, value).getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            //e.printStackTrace();
            System.out.println("No Results Found This is not an error : " + e.getMessage());
            return Optional.empty();
        }
    }

    protected Object getEntityId(T entity) {
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                    field.setAccessible(true);
                    return field.get(entity);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
