package com.aib.walletmanager.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsers")
    private Integer idUser;

    @Column(name = "nameUsers")
    private String nameUser;

    @Column(name = "lastnamteUsers")
    private String lastNameUser;

    @Column(name = "emailUser")
    private String emailUser;

    @Column(name = "passUser")
    private String passUser;

    @Column(name = "statusUser")
    private Boolean statusUser;

}
