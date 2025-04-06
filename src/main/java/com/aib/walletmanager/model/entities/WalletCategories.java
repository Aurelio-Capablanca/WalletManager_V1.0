package com.aib.walletmanager.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "WalletCategories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletCategories {

    /*
    create table WalletCategories(
idWalletCategory int primary key identity(1,1),
CategoryName varchar(30) not null
)
    */

    

}
