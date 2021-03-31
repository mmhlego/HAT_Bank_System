package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class cardCreator implements Initializable {

	@FXML
	private Label cardNumber;

	@FXML
	private Label exp;

	@FXML
	private Label cvv2;

	@FXML
	private Label iban;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Label getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Label cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Label getCvv2() {
		return cvv2;
	}

	public Label getExp() {
		return exp;
	}

	public void setExp(Label exp) {
		this.exp = exp;
	}

	public Label getIban() {
		return iban;
	}

	public void setIban(Label iban) {
		this.iban = iban;
	}

	public void setCvv2(Label cvv2) {
		this.cvv2 = cvv2;
	}

}
