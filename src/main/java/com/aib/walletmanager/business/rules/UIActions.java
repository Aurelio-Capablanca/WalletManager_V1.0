package com.aib.walletmanager.business.rules;

import com.aib.walletmanager.WalletApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UIActions {

    public static void cleanUIForms(Node item) {
        if (item instanceof TextField)
            ((TextField) item).setText("");
        if (item instanceof ComboBox<?>)
            ((ComboBox<?>) item).getSelectionModel().selectFirst();
        if (item instanceof DatePicker)
            ((DatePicker) item).setValue(null);
    }

    public static void setNewStage(ActionEvent actionEvent, String sources, String title) {
        Platform.runLater(() -> {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(WalletApp.class.getResource(sources));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.show();
        });
    }

}
