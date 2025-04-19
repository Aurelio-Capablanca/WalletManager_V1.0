package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DurationCategories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WalletDuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDuration")
    private Integer idDuration;

    @Column(name = "durationTimeset")
    private String timeSet;

}