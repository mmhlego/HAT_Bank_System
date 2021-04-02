package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.DBConnector;
import model.Transaction;
import model.User;
import model.UserController;

public class TransactionsHistory implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private AnchorPane TransactionsBoard;

	User currentUser;
	ArrayList<Transaction> allTransactions;
	SortAndSearch controller;
	String currency = " Rials";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/components/sortSearch.fxml"));
			Parent searchBox = loader.load();

			currentUser = UserController.getCurrentUser();
			allTransactions = UserController.getTransactions();

			controller = loader.getController();
			configureSortAndSearch();

			AnchorPane.setTopAnchor(searchBox, (double) 100);
			AnchorPane.setLeftAnchor(searchBox, (double) 50);
			MainPanel.getChildren().add(searchBox);

			addAllTransactions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addAllTransactions() {
		if (allTransactions.size() == 0) {
			noResults();
		}

		TransactionsBoard.getChildren().clear();

		for (int i = 0; i < allTransactions.size(); i++) {
			try {
				addTransaction(i, allTransactions.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addTransaction(int index, Transaction transaction) throws Exception {
		AnchorPane pane = FXMLLoader.load(this.getClass().getResource("../view/components/eachTransaction.fxml"));

		HBox hbox = (HBox) pane.getChildren().get(0);
		((Label) pane.getChildren().get(1)).setText(transaction.TransactionID);

		((Label) ((VBox) hbox.getChildren().get(1)).getChildren().get(0)).setText(transaction.FromAccountID);
		((Label) ((VBox) hbox.getChildren().get(1)).getChildren().get(1)).setText(transaction.ToAccountID);

		((Label) ((VBox) hbox.getChildren().get(3)).getChildren().get(0))
				.setText(Long.toString(transaction.Value) + currency);
		((Label) ((VBox) hbox.getChildren().get(3)).getChildren().get(1))
				.setText(transaction.CompletionDate.toString());

		AnchorPane.setTopAnchor(pane, (double) (index * 145));
		TransactionsBoard.getChildren().add(pane);
	}

	private void configureSortAndSearch() {
		controller.getFirstCMB().getItems().add("-");
		controller.getFirstCMB().getItems().add("Value");
		controller.getFirstCMB().getItems().add("Date");
		controller.getFirstCMB().getSelectionModel().selectFirst();

		controller.getSecondCMB().getItems().add("-");
		controller.getSecondCMB().getItems().add("ASC");
		controller.getSecondCMB().getItems().add("DESC");
		controller.getSecondCMB().getSelectionModel().selectFirst();

		controller.getThirdCMB().getItems().add("All");
		controller.getThirdCMB().getItems().add("Received");
		controller.getThirdCMB().getItems().add("Sent");
		controller.getThirdCMB().getSelectionModel().selectFirst();

		if (currentUser.AccessLevel != User.CLIENT) {
			controller.getThirdCMB().setVisible(false);
		}

		controller.getSortBTN().setOnAction(e -> {
			sort();

			search();
		});

		controller.getSearchBTN().setOnMouseClicked(e -> {
			search();
		});
	}

	private void sort() {
		String statement = "Select * From Transaction";

		if (currentUser.AccessLevel == User.CLIENT) {
			switch (controller.getThirdCMB().getValue()) {
			case "Received":
				statement += " WHERE ToAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID=\'" + currentUser.ID
						+ "\')";
				break;
			case "Sent":
				statement += " WHERE FromAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID=\'" + currentUser.ID
						+ "\')";
				break;
			default:
				statement += " WHERE FromAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID=\'" + currentUser.ID
						+ "\') OR  ToAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID=\'" + currentUser.ID
						+ "\')";
			}
		}

		if (!controller.getFirstCMB().getValue().equals("-")) {
			statement += " order by " + controller.getFirstCMB().getValue();
			if (!controller.getSecondCMB().getValue().equals("-")) {
				statement += " " + controller.getSecondCMB().getValue();
			} else {
				giveAlert("Order not specified", AlertType.ERROR);
				return;
			}
		} else if (!controller.getSecondCMB().getValue().equals("-")) {
			giveAlert("Order By not specified", AlertType.ERROR);
			return;
		}

		try {
			allTransactions = UserController.ConvertTransactionsToArrayList(DBConnector.runCommand(statement));

			addAllTransactions();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void search() {
		String search = controller.getSearchTXF().getText();

		TransactionsBoard.getChildren().clear();

		int index = 0;

		for (int i = 0; i < allTransactions.size(); i++) {
			Transaction t = allTransactions.get(i);

			if (t.FromAccountID.contains(search) || t.ToAccountID.contains(search) || t.TransactionID.contains(search)
					|| t.CompletionDate.toString().contains(search) || Long.toString(t.Value).contains(search)) {
				try {
					addTransaction(index++, allTransactions.get(i));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		if (index == 0) {
			noResults();
		}
	}

	private void noResults() {
		try {
			TransactionsBoard.getChildren().add(
					(AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/components/emptyField.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void giveAlert(String message, AlertType type) {
		Alert a = new Alert(type);
		a.setHeaderText("");
		a.setTitle("Error");
		a.setContentText(message);
		a.show();
	}
}
