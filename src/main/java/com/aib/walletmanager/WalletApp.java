package com.aib.walletmanager;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.Users;
import com.aib.walletmanager.repository.UsersRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WalletApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WalletApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connector.getInstance();
        launch();
    }
}