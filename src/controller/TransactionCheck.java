package controller;

import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;

public class TransactionCheck implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardTXF;

    @FXML
    private TextField CVVTXF;

    @FXML
    private Label requestBTN;

    @FXML
    private TextField CVV2TXF;

    @FXML
    private TextField YearTXF;

    @FXML
    private TextField MonthTXF;

    @FXML
    private JFXButton submit;

    @FXML
    private Label Recievernamelbl;

    public static int Code;
    public static String Sendercard;
    public static String Recievercard;
    public static String AmountMoney;
    public static String RecieverName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Recievernamelbl.setText(DBConnector.GetFullName(Transaction.RecieveCard));
        cardTXF.setText(Transaction.SendCard);
        LimitandNext();
        requestBTN.setOnMouseClicked((e) -> {
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
            alert("" + CreatOTP());
        });

        submit.setOnAction((e) -> {
            if (!IsAllFieldsComplete()) {
                alert("Some Fields Are Not Complete !");
            } else if (!DBConnector.CheckTransferCardInfo(cardTXF.getText(), CVV2TXF.getText(),
                    Integer.parseInt(YearTXF.getText()), Integer.parseInt(MonthTXF.getText()))) {
                alert("Wrong Credentials !");
            } else if (!DBConnector.IsCardAlive(Integer.parseInt(YearTXF.getText()),
                    Integer.parseInt(MonthTXF.getText()))) {
                alert("Card Is Expired !");
            } else if (!CVVTXF.getText().equals(String.format("%s", Code))) {
                alert("Wrong OTP");
            } else if (!DBConnector.IsMoneyEnough(Long.parseLong(Transaction.Amount), cardTXF.getText())) {
                alert("Money In Card Is Not Enough !");
            } else {
                try {
                    Sendercard = Transaction.SendCard;
                    Recievercard = Transaction.RecieveCard;
                    AmountMoney = Transaction.Amount;
                    RecieverName = DBConnector.GetFullName(Transaction.RecieveCard);
                    DBConnector.changeValue(-Long.parseLong(Transaction.Amount), cardTXF.getText());
                    DBConnector.changeValue(Long.parseLong(Transaction.Amount), Transaction.RecieveCard);
                    Sender.SendEmail(UserController.getCurrentUser().Email, "Successful Transaction ! ",
                            Sender.RecieptMail);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Transaction Was Successful ! Reciept Sent To Your Email.");
                    alert.show();
                    ClearData();
                    UserController.updatePersonalData();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/accountsPage.fxml"));
                    try {
                        MainPanel.getChildren().add(loader.load());
                    } catch (IOException e1) {
                        Alert alert1 = new Alert(AlertType.ERROR);
                        alert1.setHeaderText(null);
                        alert1.setContentText("Check Your Internet Connection !");
                        alert1.show();
                    }
                } catch (Exception exp) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Check Your Internet Connection !");
                    alert.show();
                }
            }
        });

    }

    private static int CreatOTP() {
        SecureRandom r = new SecureRandom();
        Code = r.nextInt(1000) + r.nextInt(1000);
        if (Code >= 1000 && Code <= 9999) {
            return Code;
        } else {
            return CreatOTP();
        }
    }

    private void ClearData() {
        cardTXF.setText("");
        CVVTXF.setText("");
        CVV2TXF.setText("");
        YearTXF.setText("");
        MonthTXF.setText("");
    }

    private void alert(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

    private void LimitandNext() {
        PassToNext.NextField(cardTXF, 16, true);
        PassToNext.NextField(CVVTXF, 4, true);
        PassToNext.NextField(CVV2TXF, 6, true);
        PassToNext.NextField(YearTXF, 2, true);
        PassToNext.NextField(MonthTXF, 2, true);
    }

    private boolean IsAllFieldsComplete() {
        if (cardTXF.getText().length() == 16 && CVVTXF.getText().length() == 4 && CVV2TXF.getText().length() == 6
                && YearTXF.getText().length() == 2 && MonthTXF.getText().length() == 2) {
            return true;
        } else {
            return false;
        }
    }
}