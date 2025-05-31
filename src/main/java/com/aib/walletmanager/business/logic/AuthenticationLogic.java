package com.aib.walletmanager.business.logic;

import com.aib.walletmanager.business.persistence.UserPersistence;
import com.aib.walletmanager.business.rules.AlertFactory.AlertResponses;
import com.aib.walletmanager.business.rules.AlertFactory.AlertTypes;
import com.aib.walletmanager.business.rules.businessRules.AuthenticationRules;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import javafx.scene.control.Alert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuthenticationLogic {

    private final UserPersistence userPersistence = new UserPersistence();
    private final BcryptFunction encryption = BcryptFunction.getInstance(Bcrypt.B, 12);
    private final AuthenticationRules rules = new AuthenticationRules();

    public Boolean authenticateUser(String email, String password) {
        final Optional<ResponseValidator> response = rules.validateAuthentication(email, password);
        final Alert alert;
        if (response.isPresent()) {
            alert = AlertResponses.alertResponses(AlertTypes.ERROR, "Error !", "Error at Input/Output", response.get().getMessage());
            alert.show();
            return false;
        }
        final Optional<String> encrypted = userPersistence.getUserPassword(email);
        if (encrypted.isEmpty()) {
            alert = AlertResponses.alertResponses(AlertTypes.WARNING, "Warning", "Data Mismatched!", "Your Email or Password is Incorrect");
            alert.show();
            return false;
        }
        final boolean verification = Password.check(password, encrypted.get()).with(encryption);
        if (verification) {
            UserSessionSignature.getInstance(email);
            return true;
        } else {
            alert = AlertResponses.alertResponses(AlertTypes.WARNING, "Warning", "Data Mismatched!", "Your Email or Password is Incorrect");
            alert.show();
            return false;
        }
    }

}
