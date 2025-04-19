package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Outcomes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Outcomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOutcome")
    private Integer idOutcome;

    @Column(name = "idCategoryOutcome")
    private Integer idCategoryOutcome;

    @Column(name = "OutcomeAmount")
    private BigDecimal OutcomeAmount;

    @Column(name = "idWallet")
    private Integer idWallet;

}
