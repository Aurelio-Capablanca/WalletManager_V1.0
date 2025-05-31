package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.Wallets;
import com.aib.walletmanager.repository.WalletsRepository;
import org.hibernate.Session;

import java.math.BigDecimal;

public class WalletPersistence {

  private final WalletsRepository repository = new WalletsRepository();

  public Wallets getWalletByUserId(Integer id){
      return repository.findByIntegerAttribute("idUser",id).orElse(Wallets.builder().build());
  }

//  public void updateBalances(BigDecimal amount, Integer idWallet, boolean isOutcome, Session session){
//      repository.updateBalanceByTransaction(amount, idWallet, isOutcome, session);
//  }

  public void saveWallet(Wallets item, Session session){
      repository.saveWithoutTransaction(item,session);
  }

}
