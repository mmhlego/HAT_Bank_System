package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.User;
import model.UserController;

public class AccountsShow implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private AnchorPane cardAnchor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User currentUser = UserController.getCurrentUser();
		ArrayList<Account> accounts = currentUser.getAccounts();
		System.out.println(accounts);

		int i = 0;
		for (Account account : accounts) {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/cards.fxml"));
			try {
				AnchorPane cardPage = loader.load();
				AnchorPane card = (AnchorPane) cardPage.getChildren().get(0);

				cardCreator creator = loader.getController();
				creator.getCardNumber().setText(account.BIC);
				creator.getCvv2().setText(account.CVV2);
				creator.getExp().setText(account.ExDate.getYear() + "/" + account.ExDate.getMonthValue());
				creator.getIban().setText(account.IBAN);
				card.setLayoutX((25 + (i % 2) * 420));
				card.setLayoutY((25 + i / 2 * 225));
				card.setCursor(Cursor.HAND);
				card.setOnMouseClicked(e -> {
					FXMLLoader load = new FXMLLoader(getClass().getResource("../view/AccountInformation.fxml"));
					Parent parent;
					try {
						parent = load.load();
						AccountInformation control = load.getController();
						control.getCardPlace().getChildren().clear();
						control.getCardPlace().getChildren().add(card);
						control.getCardNumberTXF().setText(account.BIC);
						control.getCvv2TXF().setText(account.CVV2);
						control.getCvvTXF().setText(account.CVV);
						control.getExpTXF().setText(account.ExDate.getYear() + "/" + account.ExDate.getMonthValue());
						MainPanel.getChildren().add(parent);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				cardAnchor.getChildren().add(card);
				i++;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (i == 0) {
			noResults();
		}
	}

	private void noResults() {
		try {
			cardAnchor.getChildren().add(
					(AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/components/emptyField.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
