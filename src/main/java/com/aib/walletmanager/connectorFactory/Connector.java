package com.aib.walletmanager.connectorFactory;

import com.aib.walletmanager.model.entities.*;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Arrays;
import java.util.List;

@Getter
public class Connector {

    private static volatile Connector instance;
    public final SessionFactory mainSession;

    private Connector() {
        mainSession = builderForSession(Arrays.asList(Users.class, Wallets.class, CategoryOutcomes.class,
                Incomes.class, Outcomes.class, SavesHistorics.class, TypeIncomes.class, WalletBudgets.class,
                WalletCategories.class, WalletDuration.class, WalletHistory.class, WalletCategories.class, WalletOrganizations.class
        ));
    }

    public static Connector getInstance() {
        Connector result = instance;
        if (result != null)
            return result;
        synchronized (Connector.class) {
            if (instance == null)
                instance = new Connector();
        }
        return instance;
    }


    private SessionFactory builderForSession(List<Class<?>> annotatedClasses) {
        final Configuration configuration = new Configuration();
        // Database Connection (Handled by HikariCP)
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlserver://;serverName=walletmanager-vonedotzero.czsm6gi8476c.us-east-2.rds.amazonaws.com;databaseName=walletManager;encrypt=false");
        configuration.setProperty("hibernate.connection.username", "adminWallet");
        configuration.setProperty("hibernate.connection.password", "jklgHnbvc555SS");
        // SQL Logging and Formatting
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.use_sql_comments", "true");
        // HikariCP Connection Pooling
        configuration.setProperty("hibernate.hikari.dataSourceClassName", "com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        configuration.setProperty("hibernate.hikari.dataSource.serverName", "localhost");
        configuration.setProperty("hibernate.hikari.dataSource.databaseName", "walletManager");
        configuration.setProperty("hibernate.hikari.maximumPoolSize", "10");
        configuration.setProperty("hibernate.hikari.minimumIdle", "1");
        configuration.setProperty("hibernate.hikari.idleTimeout", "30000");
        configuration.setProperty("hibernate.hikari.poolName", "WalletManagerPool");
        configuration.setProperty("hibernate.hikari.autoCommit", "false");
        // Batching for Performance
        configuration.setProperty("hibernate.jdbc.batch_size", "50");
        configuration.setProperty("hibernate.order_inserts", "true");
        configuration.setProperty("hibernate.order_updates", "true");
        configuration.setProperty("hibernate.batch_versioned_data", "true");
        //add all entities to hibernate
        annotatedClasses.forEach(configuration::addAnnotatedClass);
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        MetadataSources sources = new MetadataSources(registry);
        annotatedClasses.forEach(sources::addAnnotatedClass);
        Metadata data = sources.buildMetadata();
        return data.buildSessionFactory();
    }

    public Session getSessionByFactory(SessionFactory factory) {
        if (factory == null)
            throw new IllegalStateException("The session can't be made");
        return factory.openSession();
    }

    public Session getSession() {
        if (mainSession == null)
            throw new IllegalStateException("The session can't be made");
        return mainSession.openSession();
    }

    public void shutdownByFactory(SessionFactory factory) {
        if (factory != null)
            factory.close();
    }

    public void shutdown() {
        if (mainSession != null)
            mainSession.close();
    }

}
