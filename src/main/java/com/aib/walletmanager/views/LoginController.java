package com.aib.walletmanager.views;


import com.aib.walletmanager.business.logic.AuthenticationLogic;
import com.aib.walletmanager.business.rules.UIActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    AuthenticationLogic authenticationLogic = new AuthenticationLogic();

    @FXML
    private Button btnSignIn;
    @FXML
    private Button CreateNewUser;
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
            final Boolean access = authenticationLogic.authenticateUser(txtUser.getText(), txtPass.getText());
            if (access) {
                UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard");
            }
        });
        CreateNewUser.setOnAction(event -> UIActions.setNewStage(event, "CreateNewUser.fxml", "Create New User") );
    }

}
