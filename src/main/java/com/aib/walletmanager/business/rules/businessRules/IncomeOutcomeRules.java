package com.aib.walletmanager.business.rules.businessRules;

import com.aib.walletmanager.business.rules.Validator;
import com.aib.walletmanager.model.DTO.ResponseValidator;
import com.aib.walletmanager.model.dataHolders.UserSessionSignature;
import com.aib.walletmanager.model.entities.Incomes;
import com.aib.walletmanager.model.entities.Outcomes;
import com.aib.walletmanager.model.entities.WalletOrganizations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IncomeOutcomeRules {

    private final static Validator VALIDATOR = new Validator();
    private final UserSessionSignature signature = UserSessionSignature.getInstance(null);


    public Optional<ResponseValidator> validateIncomeOutcomeLogic(Outcomes out, Incomes in, WalletOrganizations organizations) {
        if (out != null) {
            if (!VALIDATOR.validateAlphabetic(out.getMotiveMovement(), 50))
                return Optional.of(ResponseValidator.builder().message("Motive only accepts string characters, no special nor numerics!")
                        .state(false).build());
            if (out.getOutcomeAmount().compareTo(signature.getWalletsInstance().getBalanceWallet()) > 0)
                return Optional.of(ResponseValidator.builder().message("You can't submit bigger amounts to withdrawal bigger than the actual balance!")
                        .state(false).build());
        }
        if (in != null) {
            if (!VALIDATOR.validateAlphabetic(in.getMotiveMovement(), 50))
                return Optional.of(ResponseValidator.builder().message("Motive only accepts string characters, no special nor numerics!")
                        .state(false).build());
        }
        return Optional.empty();
    }

}
