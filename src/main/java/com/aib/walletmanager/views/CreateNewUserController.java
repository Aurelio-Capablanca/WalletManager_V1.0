package com.aib.walletmanager.views;
import com.aib.walletmanager.business.logic.UserLogic;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.entities.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewUserController implements Initializable {

    @FXML
    private Button Login;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button BtnSignUp;

    private final UserLogic logicUser = new UserLogic();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Login.setOnAction(actionEvent -> {
            UIActions.setNewStage(actionEvent, "Login.fxml", "Login");
        });
        BtnSignUp.setOnAction(actionEvent -> {
            logicUser.saveUser(Users.builder().nameUser(txtFirstName.getText()).lastNameUser(txtLastName.getText()).passUser(txtPassword.getText()).
                    emailUser(txtEmail.getText()).statusUser(true).build());
        });
    }
}
