package com.aib.walletmanager.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CategoryOutcomes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CategoryOutcomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoryOutcome")
    private Integer IdCategoryOutcome;

    @Column(name = "categoryOutcome")
    private String categoryOutcome;

    @Column(name = "isVisible")
    private Boolean isVisible;

}
