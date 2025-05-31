package com.aib.walletmanager.business.rules.AlertFactory;

import javafx.scene.control.Alert;

public class AlertResponses {


    public static Alert alertResponses(AlertTypes type, String title, String header, String content){
        Alert response;
        switch (type)
        {
            case INFORMATION -> response = new Alert(Alert.AlertType.INFORMATION);
            case WARNING -> response = new Alert(Alert.AlertType.WARNING);
            case ERROR -> response = new Alert(Alert.AlertType.ERROR);
            case CONFIRMATION -> response = new Alert(Alert.AlertType.CONFIRMATION);
            default -> response = new Alert(Alert.AlertType.NONE);
        }
        response.setTitle(title);
        response.setHeaderText(header);
        response.setContentText(content);
        return response;
    }

}
