package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
				cardAnchor.getChildren().add(card);
				i++;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
