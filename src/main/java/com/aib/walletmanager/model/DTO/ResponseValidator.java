package com.aib.walletmanager.model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseValidator {

    private Boolean state;
    private String message;
}
