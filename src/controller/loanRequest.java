package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;
import java.security.*;

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

    public static int Code;

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

        requestBTN.setOnAction((e) -> {
            PasswordChangerController.Code = CreatOTP();
            try {
                Sender.SendEmail("mmhlegoautosmssender@gmail.com", UserController.getCurrentUser().PhoneNumber,
                        Sender.SMSMail);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("A Verification Code Was Sent To *******"
                        + UserController.getCurrentUser().PhoneNumber.substring(7, 11));
                alert.show();
            } catch (Exception e1) {
                alert("Check Your Internet Connection");
            }
        });

        confirmBTN.setOnAction((e) -> {
            Check();
        });

    }

    private void Check() {
        if (!DBConnector.CheckCurrentCVV(cvv.getText(), cardNumberCombo.getValue())) {
            alert("Wrong CVV");
        } else if (!nationalCode.getText().equals(UserController.getCurrentUser().NationalCode)) {
            alert("Wrong National Code");
        } else if (!DBConnector.CheckGuarantor(guarantorID.getText(), guarantorPassword.getText())) {
            alert("Some Of The Guarantor's Details Are Wrong !");
        } else if (months == 0 || percent == 0) {
            alert("Invalid Credentials");
        } else {
            try {
                System.out.println(amountTXF.getText());
                System.out.println(maxValueLabel.getText());
                if (Long.parseLong(amountTXF.getText()) > maxValue) {
                    alert("Your Wanted Amount Is More Than Max Value !");
                    return;
                }
                if (!securityCode.getText().equals(Integer.toString(Code))) {
                    alert("Wrong OTP");
                    return;
                }
            } catch (Exception ex) {
                alert("Value Is In Wrong Format !");
                return;
            }
            try {
                DBConnector.AddLoan(UserController.getCurrentUser().ID, getAccountID(cardNumberCombo.getValue()),
                        Long.parseLong(amountTXF.getText()), percent, guarantorID.getText(), months);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Loan Request Sended Successfully !");
                alert.show();
                UserController.updatePersonalData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/loansMainPage.fxml"));
                try {
                    MainPanel.getChildren().add(loader.load());
                } catch (IOException e1) {
                    Alert alert1 = new Alert(AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Check Your Internet Connection !");
                    alert1.show();
                }
            } catch (Exception e1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Check Your Internet Connection !");
                alert.show();
            }

        }
    }

    private String getAccountID(String BIC) {
        ArrayList<Account> all = UserController.getAccounts();

        for (Account a : all) {
            if (a.BIC.equals(BIC)) {
                return a.AccountID;
            }
        }
        return "";
    }

    private static int CreatOTP() {
        SecureRandom r = new SecureRandom();
        Code = r.nextInt(100000) + r.nextInt(100000);

        if (Code >= 100000 && Code <= 999999) {
            return Code;
        } else {
            return CreatOTP();
        }
    }

    private void alert(String Content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Content);
        alert.show();
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
