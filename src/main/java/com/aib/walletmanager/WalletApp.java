package com.aib.walletmanager;

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
        FXMLLoader fxmlLoader = new FXMLLoader(WalletApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        Connector connector = Connector.getInstance();
        connector.getSession(connector.getMainSession());
        System.out.println("ISOpen : "+connector.getMainSession().isOpen());

        Users user = Users.builder().nameUser("Aurelius").lastNameUser("Capablanca")
                .passUser("tester").statusUser(true)
                .emailUser("aurel@mail.com").build();

        UsersRepository repository = new UsersRepository();
        repository.save(user);
    }
}