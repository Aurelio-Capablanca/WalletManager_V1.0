package com.aib.walletmanager.business.rules.businessRules;

import com.aib.walletmanager.business.rules.Validator;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.WalletOrganizations;

import java.time.LocalDateTime;
import java.util.Optional;


public class BudgetRules {

    private final static Validator VALIDATOR = new Validator();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);

    public Optional<ResponseValidator> validateBudgets(WalletOrganizations org) {
        if (org.getEndDuration() != null) {
            if (org.getEndDuration().isBefore(org.getStartDuration()))
                return Optional.of(ResponseValidator.builder().state(false)
                        .message("end date can't be before start").build());
        }
        if (org.getStartDuration().isBefore(LocalDateTime.now()))
            return Optional.of(ResponseValidator.builder().state(false)
                    .message("start dates ain't supported for past dates")
                    .build());
        if (org.getBudgetAssigned().compareTo(signature.getWalletsInstance().getBalanceWallet()) > 0)
            return Optional.of(ResponseValidator.builder()
                    .state(false)
                    .message("the assigned amount can't be greater than the total balance!")
                    .build());
        if (!VALIDATOR.validateAlphanumericAndCharacters(org.getOrganizationName(), 50))
            return Optional.of(ResponseValidator.builder()
                    .state(false)
                    .message("Format not allowed!!")
                    .build());
        return Optional.empty();
    }


}
