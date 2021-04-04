package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.DBConnector;
import model.UserController;

public class InstallementPayment implements Initializable {

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
		submit.setOnAction(e -> {
			checkData();
		});
	}

	private void checkData() {
		String BIC = getBic(cardTXF.getText());
		long Value = Long.parseLong(AmountTXF.getText());
		try {
			if (!DBConnector.CheckCardInfo(BIC, cvvTXF.getText(), CVV2TXF.getText(),
					Integer.parseInt(YearTXF.getText()), Integer.parseInt(MonthTXF.getText()))) {
				alert("Wrong Data.");
			} else if (!DBConnector.IsMoneyEnough(Value, BIC)) {
				alert("Not enough money in your account.");
			} else if (!DBConnector.IsDestinationCardAlive(BIC)) {
				alert("Your card has been expired.");
			} else {
				DBConnector.changeValue(-Value, BIC);
				DBConnector.changeValue(Value, "1111111111111111");
				DBConnector.addValueToLoan(Value, cardTXF.getText());
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				DBConnector.updateLoansInDB();
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserController.updatePersonalData();

				FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/loansMainPage.fxml"));
				MainPanel.getChildren().add(loader.load());
			}
		} catch (Exception e) {
			alert("Wrong format has been entered.");
		}
	}

	private void alert(String message) {
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText("Error");
		a.setTitle("Error");
		a.setContentText(message);
		a.show();
	}

	private String getBic(String accountID) {
		ArrayList<Account> all = UserController.getAccounts();

		for (Account a : all) {
			if (a.AccountID.equals(accountID)) {
				return a.BIC;
			}
		}
		return "";
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
