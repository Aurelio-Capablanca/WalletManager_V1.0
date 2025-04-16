package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.AuthenticationLogic;
import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.model.entities.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Users user = Users.builder().nameUser("Aurelius").lastNameUser("Capablanca")
//                .passUser("jnk555").statusUser(true)
//                .emailUser("aurel@mail.com").build();
//        UserPersistence persistence = new UserPersistence();
//        persistence.saveUsers(user);

        btnSignIn.setOnAction(actionEvent -> {
            System.out.println("Trigger !!");
            authenticationLogic.authenticateUser(txtUser.getText(), txtPass.getText());
        });
    }


}
