module com.aib.walletmanager {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires com.microsoft.sqlserver.jdbc;
    requires static lombok;
    requires jakarta.persistence;
    requires password4j;

    opens com.aib.walletmanager to javafx.fxml;
    exports com.aib.walletmanager;
    exports com.aib.walletmanager.views;
    opens com.aib.walletmanager.views to javafx.fxml;

    exports com.aib.walletmanager.model.entities to org.hibernate.orm.core;
    opens com.aib.walletmanager.model.entities to org.hibernate.orm.core;
}