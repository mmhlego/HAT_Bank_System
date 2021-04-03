package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;

public class CreateCard implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField UsernameTXF;

    @FXML
    private TextField PasswordTXF;

    @FXML
    private TextField nationalCodeTXF;

    @FXML
    private TextField pinTXF;

    @FXML
    private TextField DateTXF;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private JFXButton createBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LimitandNext();
        DateTXF.setText(String.valueOf(LocalDate.now().getYear()));
        ObservableList<String> list = FXCollections.observableArrayList("Saving Account", "OnGoing Account");
        typeCombo.setItems(list);

        typeCombo.setOnAction((e) -> {
            if (typeCombo.getValue().equals("Saving Account")) {
                DateTXF.setText(String.valueOf(LocalDate.now().getYear() + 2));
            } else if (typeCombo.getValue().equals("OnGoing Account")) {
                DateTXF.setText(String.valueOf(LocalDate.now().getYear() + 5));
            }
        });

        createBTN.setOnAction((e) -> {
            if (!IsAllFieldsComplete()) {
                alert("Some Fields Are Empty");
            } else if (!DBConnector.CheckNewAccountInfo(UsernameTXF.getText(), PasswordTXF.getText(),
                    nationalCodeTXF.getText())) {
                alert("Wrong Information");
            } else {
                DBConnector.CreateNewAccount(UserController.getCurrentUser().ID, pinTXF.getText(),
                        Integer.parseInt(DateTXF.getText()), typeCombo.getSelectionModel().getSelectedIndex());
                UserController.updatePersonalData();
                try {
                    Thread.sleep(5000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Account Created Successfully !");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/accountsPage.fxml"));
                try {
                    MainPanel.getChildren().add(loader.load());
                } catch (IOException e1) {
                    Alert alert1 = new Alert(AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Check Your Internet Connection !");
                    alert1.show();
                }
            }
        });
    }

    private void LimitandNext() {
        PassToNext.NextField(pinTXF, 4, true);
        PassToNext.NextField(nationalCodeTXF, 10, true);
    }

    private void alert(String Content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Content);
        alert.show();
    }

    private boolean IsAllFieldsComplete() {
        if (!UsernameTXF.getText().equals("") && !PasswordTXF.getText().equals("")
                && nationalCodeTXF.getText().length() == 10 && pinTXF.getText().length() == 4
                && !typeCombo.getValue().equals(null)) {
                    return true;
        } else {
            return false;
        }
    }

}
