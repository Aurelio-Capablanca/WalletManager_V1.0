package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WalletBudgets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WalletBudgets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWalletBudgets")
    private Integer idBudgets;

    @Column(name = "idWallet")
    private Integer idWallet;

    @Column(name = "idOrganizer")
    private Integer idOrganizer;

}
