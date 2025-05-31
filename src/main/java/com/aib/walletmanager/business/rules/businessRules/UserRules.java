package com.aib.walletmanager.business.rules.businessRules;

import com.aib.walletmanager.business.rules.Validator;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.entities.Users;

import java.util.Optional;

public class UserRules {

    private final static Validator VALIDATOR = new Validator();

    public Optional<ResponseValidator> validateUsers(Users item){
        if(!VALIDATOR.validateAlphabetic(item.getNameUser(),50))
            return Optional.of(ResponseValidator.builder()
                            .state(false).message("name just allow characters!!!")
                    .build());
        if(!VALIDATOR.validateAlphabetic(item.getLastNameUser(),50))
            return Optional.of(ResponseValidator.builder()
                    .state(false).message("last name just allow characters!!!")
                    .build());
        if(!VALIDATOR.validateEmails(item.getEmailUser(),50))
            return Optional.of(ResponseValidator.builder()
                    .state(false).message("format of Email not present")
                    .build());
        if(!VALIDATOR.validatePass(item.getPassUser(),15))
            return Optional.of(ResponseValidator.builder()
                    .state(false).message("Format for password not allowed !")
                    .build());
        return Optional.empty();
    }

}
