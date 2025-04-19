package com.aib.walletmanager.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WalletCategories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WalletCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWalletCategory")
    private Integer idWalletCategory;

    @Column(name = "CategoryName")
    private String categoryWallet;

}
