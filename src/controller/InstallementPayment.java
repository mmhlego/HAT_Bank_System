package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InstallementPayment implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardTXF;

    @FXML
    private TextField AmountTXF;

    @FXML
    private TextField cvvTXF;

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

	public TextField getCvvTXF() {
		return cvvTXF;
	}

	public void setCvvTXF(TextField cvvTXF) {
		this.cvvTXF = cvvTXF;
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
