package controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;

public class Withdraw implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardTXF;

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
        LimitandNext();
        submit.setOnAction((e) -> {
            if (!IsAllFieldsComplete()) {
                alert("Some Fields Are Empty !");
            } else if (!DBConnector.containsBIC(cardTXF.getText())) {
                alert("Card Is Inavalid !");
            } else if (!DBConnector.IsMoneyEnough(Long.parseLong(AmountTXF.getText()), cardTXF.getText())) {
                alert("Money In Card Is Not Enough !");
            } else if (!DBConnector.CheckCardInfo(cardTXF.getText(), pinTXF.getText(), CVV2TXF.getText(),
                    Integer.parseInt(YearTXF.getText()), Integer.parseInt(MonthTXF.getText()))) {
                alert("Wrong Credentials !");
            } else if (!DBConnector.IsCardAlive(Integer.parseInt(YearTXF.getText()),
                    Integer.parseInt(MonthTXF.getText()))) {
                alert("Card Is Expired !");
            }
            else {
                DBConnector.changeValue(-Long.parseLong(AmountTXF.getText()), cardTXF.getText());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Transaction Was Successful !");
                alert.show();
                ClearData();
            }
        });
    }

    private void LimitandNext() {
        PassToNext.NextField(cardTXF, 16);
        PassToNext.NextField(pinTXF, 4);
        PassToNext.NextField(AmountTXF, 18);
        PassToNext.NextField(CVV2TXF, 6);
        PassToNext.NextField(YearTXF, 2);
        PassToNext.NextField(MonthTXF, 2);
    }

    private boolean IsAllFieldsComplete() {
        if (cardTXF.getText().length() == 16 && !(AmountTXF.getText().equals("")) && pinTXF.getText().length() == 4
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
        cardTXF.setText("");
        AmountTXF.setText("");
        pinTXF.setText("");
        CVV2TXF.setText("");
        YearTXF.setText("");
        MonthTXF.setText("");
    }

}
