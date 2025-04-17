package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.WalletsRepository;

public class WalletPersistence {

  private final WalletsRepository repository = new WalletsRepository();

  public Wallets getWalletByUserId(Integer id){
      return repository.findByIntegerAttribute("idUser",id).orElse(Wallets.builder().build());
  }

}
