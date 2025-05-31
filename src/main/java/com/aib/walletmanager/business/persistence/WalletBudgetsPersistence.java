package com.aib.walletmanager.business.persistence;

import com.aib.walletmanager.model.entities.WalletBudgets;
import com.aib.walletmanager.repository.WalletBudgetsRepository;
import org.hibernate.Session;

import java.util.List;

public class WalletBudgetsPersistence {

    private final WalletBudgetsRepository repository = new WalletBudgetsRepository();

    public void saveComposition(WalletBudgets items, Session session) {
        repository.saveWalletBudget(items, session);
    }

    public List<WalletBudgets> getRelatedToAnOrg(Integer idWallet, Integer idOrganization) {
        return repository.getRelatedToAnOrg(idWallet, idOrganization);
    }

//    public void delete(WalletBudgets item, Session session) {
//        repository.deleteWithoutTransaction(item, session);
//    }
    public void deleteAll(List<WalletBudgets> items, Session session){
        items.forEach(item-> repository.deleteWalletBudget(item.getIdBudgets(), session));
    }

}
