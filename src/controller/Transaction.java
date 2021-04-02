package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;

public class Transaction implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private TextField fromTXF;

	@FXML
	private TextField toTXF;

	@FXML
	private TextField amountTXF;

	@FXML
	private JFXButton nextBTN;

	public static String SendCard;
	public static String RecieveCard;
	public static String Amount;

	Parent next;
	boolean check = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LimitAndNext();
		nextBTN.setOnAction((e) -> {
			if (IsAllFieldsComplete()) {
				if (!DBConnector.containsBIC(fromTXF.getText())) {
					alert("Your Card Number Is Not Valid !");
				} else if (DBConnector.GetFullName(toTXF.getText()).equals("")) {
					alert("Destination Card Is Wrong !");
				} else if (!DBConnector.IsDestinationCardAlive(toTXF.getText())) {
					alert("Destination Card Is Wrong !");
				} else if (Long.parseLong(amountTXF.getText()) > 5000000) {
					alert("Can't Transfer More Than 5000000 via Card");
				} else if (!DBConnector.CheckCardOwner(fromTXF.getText(), UserController.getCurrentUser().ID)) {
					alert("You Don't Own This Card !");
				} else {
					SendCard = fromTXF.getText();
					RecieveCard = toTXF.getText();
					Amount = amountTXF.getText();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TransactionsResume.fxml"));
					try {
						MainPanel.getChildren().add(loader.load());
					} catch (IOException e1) {

					}
				}
			} else {
				alert("Wrong Credentials !");
			}
		});
	}

	private void alert(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.show();
	}

	private void LimitAndNext() {
		PassToNext.NextField(fromTXF, 16, true);
		PassToNext.NextField(toTXF, 16, true);
		PassToNext.NextField(amountTXF, 7, true);
	}

	private boolean IsAllFieldsComplete() {
		if (fromTXF.getText().length() == 16 && toTXF.getText().length() == 16 && !amountTXF.getText().equals("")) {
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

	public TextField getFromTXF() {
		return fromTXF;
	}

	public void setFromTXF(TextField fromTXF) {
		this.fromTXF = fromTXF;
	}

	public TextField getToTXF() {
		return toTXF;
	}

	public void setToTXF(TextField toTXF) {
		this.toTXF = toTXF;
	}

	public TextField getAmountTXF() {
		return amountTXF;
	}

	public void setAmountTXF(TextField amountTXF) {
		this.amountTXF = amountTXF;
	}

	public JFXButton getNextBTN() {
		return nextBTN;
	}

	public void setNextBTN(JFXButton nextBTN) {
		this.nextBTN = nextBTN;
	}

	public Parent getNext() {
		return next;
	}

	public void setNext(Parent next) {
		this.next = next;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}