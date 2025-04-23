package com.aib.walletmanager.views;

import com.aib.walletmanager.WalletApp;
import com.aib.walletmanager.business.logic.AuthenticationLogic;
import com.aib.walletmanager.business.logic.UserLogic;
import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.entities.Users;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    AuthenticationLogic authenticationLogic = new AuthenticationLogic();

    @FXML
    private Button btnSignIn;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private AnchorPane anpLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anpLogin.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                btnSignIn.fire();
            }
        });
        btnSignIn.setOnAction(actionEvent -> {
            System.out.println("Trigger !!");
            final Boolean access = authenticationLogic.authenticateUser(txtUser.getText(), txtPass.getText());
            if (access) {
                UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard");
            }
        });
    }

}
