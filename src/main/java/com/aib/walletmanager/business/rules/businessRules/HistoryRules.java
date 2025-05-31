package com.aib.walletmanager.business.rules.businessRules;

import com.aib.walletmanager.model.DTO.ResponseValidator;

import java.time.LocalDate;
import java.util.Optional;

public class HistoryRules {

    public Optional<ResponseValidator> validateHistoric(LocalDate from, LocalDate to) {
        if (from.isAfter(to))
            return Optional.of(ResponseValidator.builder()
                    .state(false).message("From can't be after the final date!!")
                    .build());
        return Optional.empty();
    }

}
