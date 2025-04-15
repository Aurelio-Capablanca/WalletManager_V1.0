package com.aib.walletmanager.connectorFactory;

import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.model.entities.Wallets;
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

    public Connector() {
        mainSession = builderForSession(Arrays.asList(Users.class, Wallets.class));
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
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlserver://localhost;databaseName=walletManager;encrypt=false");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "jklgHnbvc555SS");

        // SQL Logging and Formatting
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.use_sql_comments", "true");

        // HikariCP Connection Pooling
        configuration.setProperty("hibernate.hikari.dataSourceClassName", "com.microsoft.sqlserver.jdbc.SQLServerDataSource");
        configuration.setProperty("hibernate.hikari.dataSource.serverName", "localhost");
        configuration.setProperty("hibernate.hikari.dataSource.databaseName", "walletManager");
        configuration.setProperty("hibernate.hikari.dataSource.user", "sa");
        configuration.setProperty("hibernate.hikari.dataSource.password", "jklgHnbvc555SS");
        configuration.setProperty("hibernate.hikari.maximumPoolSize", "10");
        configuration.setProperty("hibernate.hikari.minimumIdle", "1");
        configuration.setProperty("hibernate.hikari.idleTimeout", "30000");
        configuration.setProperty("hibernate.hikari.poolName", "WalletManagerPool");
        configuration.setProperty("hibernate.hikari.autoCommit", "false");

        //configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");

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

    public Session getSession(SessionFactory factory) {
        if (factory == null)
            throw new IllegalStateException("The session can't be made");
        return factory.openSession();
    }

    public void shutdown(SessionFactory factory) {
        if (factory != null)
            factory.close();
    }

}
