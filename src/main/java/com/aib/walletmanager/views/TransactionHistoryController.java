package com.aib.walletmanager.views;

import com.aib.walletmanager.business.logic.WalletHistoryLogic;
import com.aib.walletmanager.business.rules.UIActions;
import com.aib.walletmanager.model.entities.WalletHistory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TransactionHistoryController implements Initializable {


    @FXML
    Button btnReturn;
    @FXML
    Button btnSearch;
    @FXML
    DatePicker tpFrom;
    @FXML
    DatePicker tpTo;
    @FXML
    ListView<WalletHistory> ltvSearched;

    private final WalletHistoryLogic historyLogic = new WalletHistoryLogic();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setOnAction(actionEvent -> {
            Optional<List<WalletHistory>> historical = Optional.ofNullable(historyLogic.searchHistoric(tpFrom.getValue(), tpTo.getValue()));
            if (historical.isEmpty()) {
                return;
            }
            ltvSearched.setItems(FXCollections.observableList(historical.get()));
        });
        btnReturn.setOnAction(actionEvent -> UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard"));
        ltvSearched.setCellFactory(listView -> new ListCell<WalletHistory>() {
            @Override
            protected void updateItem(WalletHistory item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd yyyy");
                    BigDecimal movedAmount = item.getAmountOutcome().compareTo(BigDecimal.ZERO) == 0 ? item.getAmountIncome() : item.getAmountOutcome();
                    setText("Moved Amount: " + movedAmount + " Current Balance: " + item.getBalanceWallet() + " Previous: " + item.getPreviousBalanceWallet() + " Date:  " + format.format(item.getDateSpent()));
                }
            }
        });
    }

}
