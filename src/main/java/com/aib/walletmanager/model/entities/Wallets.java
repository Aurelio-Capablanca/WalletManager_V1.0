package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Wallet")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wallets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWallet")
    private Integer idWallet;

    @Column(name = "codeWallet")
    private String codeWallet;

    @Column(name = "balanceWallet")
    private BigDecimal balanceWallet;

    @Column(name = "idUser")
    private Integer idUser;
}
