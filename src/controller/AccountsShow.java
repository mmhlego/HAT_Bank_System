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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Account;
import model.DBConnector;
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

		int i = 0;
		for (Account account : accounts) {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/cards.fxml"));

			if (currentUser.AccessLevel == User.CLIENT) {
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
							if (!DBConnector.IsCardAlive(account.ExDate.getYear(), account.ExDate.getMonthValue())) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Expired");
								alert.setHeaderText("Your Card is Expired");
								alert.show();
								control.getDepositBTn().setDisable(true);
								control.getWithdrawBTN().setDisable(true);
								control.getHistoryBTN().setDisable(true);
							}
							if (account.Status == Account.SAVEDACCOUNT) {
								control.getMode().setText("Saved Account");
							} else {
								control.getMode().setText("Ongoing Account");
							}
							control.getCardNumber().setText(account.BIC);
							control.getCvv2().setText(account.CVV2);
							control.getExp().setText(account.ExDate.getYear() + "/" + account.ExDate.getMonthValue());
							control.getIban().setText(account.IBAN);

							control.getCardNumberTXF().setText(account.BIC);
							control.getCvv2TXF().setText(account.CVV2);
							control.getCvvTXF().setText(account.CVV);
							control.getExpTXF()
									.setText(account.ExDate.getYear() + "/" + account.ExDate.getMonthValue());
							MainPanel.getChildren().add(parent);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
					cardAnchor.getChildren().add(card);

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					FXMLLoader l = new FXMLLoader(this.getClass().getResource("../view/components/eachAccount.fxml"));
					AnchorPane each = l.load();
					eachAccount controller = l.getController();

					controller.getAccountId().setText(account.AccountID);
					controller.getOwnerId().setText(account.OwnerID);
					controller.getBic().setText(account.BIC);
					controller.getIban().setText(account.IBAN);
					controller.getAmount1().setText(account.CVV2);
					switch (account.Status) {
					case Account.SAVEDACCOUNT:
						controller.getStatus().setText("Saved");
						break;

					case Account.OngoingAccount:
						controller.getStatus().setText("Ongoing");
						break;
					}

					each.setLayoutY((double) i * 145);
					cardAnchor.getChildren().add(each);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			i++;
		}
		if (currentUser.AccessLevel==User.CLIENT) {
			FXMLLoader loader=new FXMLLoader(this.getClass().getResource("../view/components/plus.fxml"));
			try {
				AnchorPane plusCard=loader.load();
				plusCard.setLayoutX((25 + (i % 2) * 420));
				plusCard.setLayoutY((25 + i / 2 * 225));
				
				plusCard.setOnMouseClicked(e->{
					FXMLLoader loaders = new FXMLLoader(getClass().getResource("../view/cardCreatePage.fxml"));
					try {
						MainPanel.getChildren().add(loaders.load());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				((ImageView)plusCard.getChildren().get(0)).setOnMouseClicked(e->{
					FXMLLoader loaders = new FXMLLoader(getClass().getResource("../view/cardCreatePage.fxml"));
					try {
						MainPanel.getChildren().add(loaders.load());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				cardAnchor.getChildren().add(plusCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
			
			
	}

}
