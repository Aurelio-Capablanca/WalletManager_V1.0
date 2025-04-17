package com.aib.walletmanager.model.dataHolders;

import com.aib.walletmanager.business.logic.*;
import com.aib.walletmanager.connectorFactory.Connector;
import com.aib.walletmanager.model.entities.*;
import lombok.Getter;

import java.util.List;


public class UserSessionSignature {

    private static volatile UserSessionSignature instance;
    @Getter
    private Users usersInstance;
    @Getter
    private Wallets walletsInstance;
    @Getter
    private List<WalletDuration> walletDurations;
    @Getter
    private List<WalletCategories> walletCategories;
    @Getter
    private List<TypeIncomes> typeIncomes;
    @Getter
    private List<CategoryOutcomes> categoryOutcomes;

    private final UserLogic userLogic = new UserLogic();
    private final WalletLogic walletLogic = new WalletLogic();
    private final WalletDurationLogic walletDurationLogic = new WalletDurationLogic();
    private final WalletCategoryLogic walletCategoryLogic = new WalletCategoryLogic();
    private final TypeIncomesLogic typeIncomesLogic = new TypeIncomesLogic();
    private final CategoryOutcomesLogic categoryOutcomesLogic = new CategoryOutcomesLogic();

    private UserSessionSignature(String emailUser) {
        if (emailUser == null)
            return;
        setInstances(emailUser);
    }

    public static UserSessionSignature getInstance(String emailUser) {
        UserSessionSignature result = instance;
        if (result != null)
            return result;
        synchronized (UserSessionSignature.class) {
            if (instance == null && emailUser != null)
                instance = new UserSessionSignature(emailUser);
        }
        return instance;
    }


    private void setInstances(String email) {
        usersInstance = userLogic.findByEmail(email);
        walletsInstance = walletLogic.getWalletByUserId(usersInstance.getIdUser());
        walletDurations = walletDurationLogic.getAll();
        walletCategories = walletCategoryLogic.getAll();
        typeIncomes = typeIncomesLogic.getAll();
        categoryOutcomes = categoryOutcomesLogic.getAll();
    }

}
