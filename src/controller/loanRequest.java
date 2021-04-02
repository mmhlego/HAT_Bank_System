package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.*;

public class loanRequest implements Initializable{
@FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardNumber;

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
    private TextField amount;

    @FXML
    private ComboBox<?> mode;

    @FXML
    private Label maxValue;

    @FXML
    private Label status;

    @FXML
    private Label installment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        LimitAndNext();
    }

    private void LimitAndNext() {
        PassToNext.NextField(cardNumber, 16, true);
        PassToNext.NextField(cvv, 4, true);
        PassToNext.NextField(nationalCode, 10, true);
        PassToNext.NextField(guarantorID, 7, false);
        PassToNext.NextField(guarantorPassword, 20, true);
        // PassToNext.NextField(dueDate, 16 , true);
        PassToNext.NextField(securityCode, 6, true);
    }

}
