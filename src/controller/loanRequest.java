package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.*;

public class loanRequest implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private ComboBox<String> cardNumberCombo;

    @FXML
    private TextField cvv;

    @FXML
    private TextField nationalCode;

    @FXML
    private TextField guarantorID;

    @FXML
    private TextField guarantorPassword;

    @FXML
    private TextField securityCode;

    @FXML
    private JFXButton requestBTN;

    @FXML
    private JFXButton confirmBTN;

    @FXML
    private TextField amountTXF;

    @FXML
    private ComboBox<String> mode;

    @FXML
    private Label maxValueLabel;

    @FXML
    private Label status;

    @FXML
    private Label installment;

    int percent = 0, months = 0, AccountStatus = -1;
    long amount = 0, allAmount = 0, maxValue, mil = 10000000;
    String cardNumber;
    long[][] values = { { 1 * mil, 2 * mil }, { 3 * mil, 4 * mil }, { 5 * mil, 6 * mil }, { 7 * mil, 8 * mil },
            { 9 * mil, 10 * mil }, { 11 * mil, 12 * mil } };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCards();
        LimitAndNext();
        addModes();
        addChanger();
    }

    private void addCards() {
        ArrayList<Account> all = UserController.getAccounts();

        for (Account a : all) {
            cardNumberCombo.getItems().add(a.BIC);
        }
    }

    private void LimitAndNext() {
        PassToNext.NextField(cvv, 4, true);
        PassToNext.NextField(nationalCode, 10, true);
        PassToNext.NextField(guarantorID, 7, false);
        PassToNext.NextField(guarantorPassword, 20, false);
        PassToNext.NextField(securityCode, 6, true);
        PassToNext.NextField(amountTXF, 18, true);
    }

    private void addModes() {
        mode.getItems().add("9 Months - 5 Percent");
        mode.getItems().add("12 Months - 10 Percent");
        mode.getItems().add("24 Months - 15 Percent");
        mode.getItems().add("36 Months - 20 Percent");
        mode.getItems().add("48 Months - 25 Percent");
        mode.getItems().add("60 Months - 30 Percent");
    }

    private int getMode(String BIC) {
        ArrayList<Account> all = UserController.getAccounts();

        for (Account a : all) {
            if (a.BIC.equals(BIC)) {
                return a.Status;
            }
        }
        return -1;
    }

    private void addChanger() {
        cardNumberCombo.setOnAction(e -> {
            cardNumber = cardNumberCombo.getValue();

            AccountStatus = getMode(cardNumberCombo.getValue());

            switch (AccountStatus) {
            case Account.SAVEDACCOUNT:
                status.setText("Account Mode : Saved Account");
                break;

            case Account.OngoingAccount:
                status.setText("Account Mode : Ongoing Account");
                break;

            default:
                status.setText("Account Mode");
                break;
            }

            setMaxValue();
        });

        mode.setOnAction(e -> {
            String text = mode.getValue();

            months = Integer.parseInt(text.split(" ")[0]);
            percent = Integer.parseInt(text.split(" ")[3]);
            setMaxValue();

            try {
                amount = Long.parseLong(amountTXF.getText());
                allAmount = amount * (100 + percent) / 100;
                installment.setText("Installment : " + Long.toString(allAmount / months) + " Rials");
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
    }

    private void setMaxValue() {
        if (AccountStatus != -1 && months != 0) {
            maxValue = values[mode.getSelectionModel().getSelectedIndex()][AccountStatus];

            maxValueLabel.setText("Max Value : " + Long.toString(maxValue) + " Rials");
        }
    }
}
