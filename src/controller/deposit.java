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
import model.DBConnector;
import model.PassToNext;

public class deposit implements Initializable {

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
    public void initialize(URL arg0, ResourceBundle arg1) {
        LimitandNext();
        submit.setOnAction((e) -> {
            if (!IsAllFieldsComplete()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Some Fields Are Empty !");
                alert.show();
            } else if (!DBConnector.containsBIC(cardTXF.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Wrong Credentials !");
                alert.show();
            } else{
                DBConnector.changeValue(Long.parseLong(AmountTXF.getText()),cardTXF.getText());
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

	public AnchorPane getMainPanel() {
		return MainPanel;
	}

	public void setMainPanel(AnchorPane mainPanel) {
		MainPanel = mainPanel;
	}

	public TextField getCardTXF() {
		return cardTXF;
	}

	public void setCardTXF(TextField cardTXF) {
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
