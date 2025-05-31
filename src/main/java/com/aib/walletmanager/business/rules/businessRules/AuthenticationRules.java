package com.aib.walletmanager.business.rules.businessRules;

import com.aib.walletmanager.business.rules.Validator;
import com.aib.walletmanager.model.DTO.ResponseValidator;

import java.util.Map;
import java.util.Optional;

public class AuthenticationRules {

    private final static Validator VALIDATOR = new Validator();

    public Optional<ResponseValidator> validateAuthentication(String email, String pass) {
        if (!VALIDATOR.validateEmails(email, 50))
            return Optional.of(ResponseValidator.builder().state(false)
                    .message("Email format non present").build());
        if (!VALIDATOR.validateAlphanumericAndCharacters(pass, 16))
            return Optional.of(ResponseValidator.builder()
                    .state(false)
                    .message("incorrect format for password").build());
        return Optional.empty();
    }

}
