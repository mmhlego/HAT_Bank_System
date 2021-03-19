package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AccountDataController implements Initializable {
    @FXML
    private AnchorPane panel;
    @FXML
    private Button withdraw;
    @FXML
    private Button deposit;
    @FXML
    private Button history;
    @FXML
    private Button edit;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        panel.setOnMouseClicked(e -> {
            System.out.println(1);
        });
    }
}
