package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import model.*;

public class Withdraw implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private ComboBox<String> cardTXF;

    @FXML
    private TextField AmountTXF;

    @FXML
    private TextField pinTXF;

    @FXML
    private TextField CVV2TXF;

    @FXML
    private TextField YearTXF;

    @FXML
    private TextField MonthTXF;

    @FXML
    private JFXButton submit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCards();
        LimitandNext();
        submit.setOnAction((e) -> {
            if (!IsAllFieldsComplete()) {
                alert("Some Fields Are Empty !");
            } else if (!DBConnector.containsBIC(cardTXF.getValue())) {
                alert("Card Is Inavalid !");
            } else if (!DBConnector.CheckCardInfo(cardTXF.getValue(), pinTXF.getText(), CVV2TXF.getText(),
                    Integer.parseInt(YearTXF.getText()), Integer.parseInt(MonthTXF.getText()))) {
                alert("Wrong Credentials !");
            } else if (!DBConnector.IsCardAlive(Integer.parseInt(YearTXF.getText()),
                    Integer.parseInt(MonthTXF.getText()))) {
                alert("Card Is Expired !");
            } else if (!DBConnector.IsMoneyEnough(Long.parseLong(AmountTXF.getText()), cardTXF.getValue())) {
                alert("Money In Card Is Not Enough !");
            } else if (!DBConnector.CheckCardOwner(cardTXF.getValue(), UserController.getCurrentUser().ID)) {
                alert("You Don't Own This Card !");
            } else {
                try {
                    DBConnector.changeValue(-Long.parseLong(AmountTXF.getText()), cardTXF.getValue());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Transaction Was Successful !");
                    alert.show();
                    ClearData();
                } catch (Exception exp) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Check Your Internet Connection !");
                    alert.show();
                }
            }
        });
    }

    private void LimitandNext() {
        PassToNext.NextField(pinTXF, 4, true);
        PassToNext.NextField(AmountTXF, 18, true);
        PassToNext.NextField(CVV2TXF, 6, true);
        PassToNext.NextField(YearTXF, 2, true);
        PassToNext.NextField(MonthTXF, 2, true);
    }

    private boolean IsAllFieldsComplete() {
        if (cardTXF.getValue().length() == 16 && !(AmountTXF.getText().equals("")) && pinTXF.getText().length() == 4
                && CVV2TXF.getText().length() == 6 && YearTXF.getText().length() == 2
                && MonthTXF.getText().length() == 2) {
            return true;
        } else {
            return false;
        }
    }

    private void alert(String Content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(Content);
        alert.show();
    }

    private void ClearData() {
        cardTXF.setValue("");
        AmountTXF.setText("");
        pinTXF.setText("");
        CVV2TXF.setText("");
        YearTXF.setText("");
        MonthTXF.setText("");
    }

    private void addCards() {
        ArrayList<Account> all = UserController.getAccounts();

        for (Account a : all) {
            cardTXF.getItems().add(a.BIC);
        }
    }

    public AnchorPane getMainPanel() {
        return MainPanel;
    }

    public void setMainPanel(AnchorPane mainPanel) {
        MainPanel = mainPanel;
    }

    public ComboBox<String> getCardTXF() {
        return cardTXF;
    }

    public void setCardTXF(ComboBox<String> cardTXF) {
        this.cardTXF = cardTXF;
    }

    public TextField getAmountTXF() {
        return AmountTXF;
    }

    public void setAmountTXF(TextField amountTXF) {
        AmountTXF = amountTXF;
    }

    public TextField getPinTXF() {
        return pinTXF;
    }

    public void setPinTXF(TextField pinTXF) {
        this.pinTXF = pinTXF;
    }

    public TextField getCVV2TXF() {
        return CVV2TXF;
    }

    public void setCVV2TXF(TextField cVV2TXF) {
        CVV2TXF = cVV2TXF;
    }

    public TextField getYearTXF() {
        return YearTXF;
    }

    public void setYearTXF(TextField yearTXF) {
        YearTXF = yearTXF;
    }

    public TextField getMonthTXF() {
        return MonthTXF;
    }

    public void setMonthTXF(TextField monthTXF) {
        MonthTXF = monthTXF;
    }

    public JFXButton getSubmit() {
        return submit;
    }

    public void setSubmit(JFXButton submit) {
        this.submit = submit;
    }

}