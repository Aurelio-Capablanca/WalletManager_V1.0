package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WalletHistory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WalletHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoryWallet")
    private Integer idWalletHistory;

    @Column(name = "balanceWallet")
    private BigDecimal balanceWallet;

    @Column(name = "previousBalance")
    private BigDecimal previousBalanceWallet;

    @Column(name = "amountOutcome")
    private BigDecimal amountOutcome;

    @Column(name = "amountIncome")
    private BigDecimal amountIncome;

    @Column(name = "dateSpent")
    private LocalDateTime dateSpent;

    @Column(name = "idWallet")
    private Integer idWallet;
}
