package com.aib.walletmanager.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceOrigins {

    private LinkedHashMap<String, Integer> walletBalances = new LinkedHashMap<>();

    public void setBalances(String key, Integer value) {
        walletBalances.put(key, value);
    }

}
