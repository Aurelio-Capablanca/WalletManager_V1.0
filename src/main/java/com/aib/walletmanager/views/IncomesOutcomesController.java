package com.aib.walletmanager.views;

import com.aib.walletmanager.business.rules.UIActions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class IncomesOutcomesController implements Initializable {

    @FXML
    Button btnReturn;
    @FXML
    Button btnLogout;
    @FXML
    TreeView<String> treeView;
    @FXML
    ComboBox<?> cmbCategory;
    @FXML
    TextField txtAmount;
    @FXML
    RadioButton rdbOutcomeActivation;
    @FXML
    Button btnSave;
    @FXML
    Button btnCancel;
    @FXML
    TextArea txaMotive;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReturn.setOnAction(actionEvent -> UIActions.setNewStage(actionEvent, "Dashboard.fxml", "Dashboard"));
        
    }
}
