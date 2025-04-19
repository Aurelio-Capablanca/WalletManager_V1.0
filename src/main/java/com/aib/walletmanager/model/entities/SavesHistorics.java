package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SavesHistorics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SavesHistorics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSaves")
    private Integer idSaveHistory;

    @Column(name = "creationSaves")
    private LocalDateTime creationSaves;

    @Column(name = "percentageSaved")
    private Double percentageSaves;

    @Column(name = "amountSaved")
    private BigDecimal amountSaves;

    @Column(name = "amountTotalInSaves")
    private BigDecimal amountTotalInSaves;


}
