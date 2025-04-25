package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "dateOutcome")
    private LocalDateTime dateOutcome;

    @Column(name = "Motive")
    private String motiveMovement;

    @Column(name = "idWallet")
    private Integer idWallet;

}
