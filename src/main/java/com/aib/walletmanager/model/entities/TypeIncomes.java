package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TypeIncomes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TypeIncomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTypeIncome")
    private Integer idTypeIncome;

    @Column(name = "typeIncome")
    private String typeIncome;

    @Column(name = "isVisible")
    private Boolean isVisible;


}
