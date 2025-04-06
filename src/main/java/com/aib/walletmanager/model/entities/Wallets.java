package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Wallet")
@Getter
@Setter
@Builder
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
    private Double balanceWallet;

    @Column(name = "idUser")
    private Integer idUser;
}
