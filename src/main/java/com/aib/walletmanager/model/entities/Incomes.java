package com.aib.walletmanager.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "Incomes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Incomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIncome")
    private Integer idIncomes;

    @Column(name = "amountIncome")
    private BigDecimal amountIncome;

    @Column(name = "dateIncome")
    private LocalDateTime dateIncome;

    @Column(name = "Motive")
    private String motiveMovement;

    @Column(name = "idTypeIncome")
    private Integer typeIncome;

    @Column(name = "idWallet")
    private Integer walletId;
}
