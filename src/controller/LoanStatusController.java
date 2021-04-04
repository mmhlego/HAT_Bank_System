package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.DBConnector;
import model.Loan;
import model.User;
import model.UserController;

public class LoanStatusController implements Initializable {
	@FXML
	private AnchorPane MainPanel;

	@FXML
	private AnchorPane loansBoard;
	User currentUser;
	ArrayList<Loan> allLoans;
	SortAndSearch controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/components/sortSearch.fxml"));
			Parent searchBox = loader.load();

			currentUser = UserController.getCurrentUser();
			allLoans = UserController.getLoans();

			controller = loader.getController();
			configureSortAndSearch();

			AnchorPane.setTopAnchor(searchBox, (double) 100);
			AnchorPane.setLeftAnchor(searchBox, (double) 50);
			MainPanel.getChildren().add(searchBox);

			addAllLoans();

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	int extra = 0;
	String currency = " Rials";

	private void addAllLoans() {
		if (allLoans.size() == 0) {
			noResults();
		}

		loansBoard.getChildren().clear();

		extra = 0;
		for (int i = 0; i < allLoans.size(); i++) {
			try {
				addLoan(i, allLoans.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addLoan(int index, Loan loan) throws Exception {
		AnchorPane pane = null;

		switch (loan.Status) {
		case Loan.PENDING:
			pane = FXMLLoader.load(this.getClass().getResource("../view/components/pendingLoanAnchor.fxml"));
			break;
		case Loan.REJECTED:
			pane = FXMLLoader.load(this.getClass().getResource("../view/components/rejectedLoanAnchor.fxml"));
			break;
		case Loan.PAYING:
			pane = FXMLLoader.load(this.getClass().getResource("../view/components/acceptedLoanAnchor.fxml"));
			break;
		case Loan.FINISHED:
			pane = FXMLLoader.load(this.getClass().getResource("../view/components/finishedLoanAnchor.fxml"));
			break;
		}

		Group group = (Group) pane.getChildren().get(1);
		((Label) group.getChildren().get(1)).setText(String.valueOf(loan.Value) + currency);
		((Label) group.getChildren().get(3)).setText(loan.AccountID);

		AnchorPane.setTopAnchor(pane, (double) (index * 80 + extra));
		loansBoard.getChildren().add(pane);

		if (loan.Status == Loan.PAYING) {
			double percent = loan.Payed / (double) loan.TotalPay;

			((Label) group.getChildren().get(5)).setText(Long.toString(loan.Payed) + currency);
			((JFXProgressBar) pane.getChildren().get(2)).setProgress(percent);
			((Label) pane.getChildren().get(3)).setText(String.format("%,.1f", percent * 100) + "%");

			if (percent < 0.5) {
				((Label) pane.getChildren().get(3)).setStyle("-fx-text-fill: #000;");
			}

			if (currentUser.AccessLevel == User.MANAGER || currentUser.AccessLevel == User.EMPLOYEE) {
				group.getChildren().get(6).setVisible(false);
			} else {
				JFXButton btn = (JFXButton) group.getChildren().get(6);
				btn.setOnAction(e -> {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/InstallementPayment.fxml"));
					try {
						MainPanel.getChildren().add(loader.load());
						InstallementPayment installementPayment = loader.getController();
						installementPayment.getCardTXF().setText(loan.AccountID);

						int[] months = { 9, 12, 24, 36, 48, 60 };

						installementPayment.getAmountTXF()
								.setText(String.valueOf(loan.TotalPay / (months[loan.Percntage / 5 - 1])));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
			}

			extra += 120;
		} else if (loan.Status == Loan.PENDING) {
			if (currentUser.AccessLevel == User.CLIENT) {
				((Label) pane.getChildren().get(4)).setVisible(true);
			} else if (currentUser.AccessLevel == User.MANAGER || currentUser.AccessLevel == User.EMPLOYEE) {
				((JFXButton) pane.getChildren().get(2)).setVisible(true);
				((JFXButton) pane.getChildren().get(2)).setOnAction(e -> {
					DBConnector.updateLoan(loan.AccountID, Loan.PAYING);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					sort();
				});

				((JFXButton) pane.getChildren().get(3)).setVisible(true);
				((JFXButton) pane.getChildren().get(3)).setOnAction(e -> {
					DBConnector.updateLoan(loan.AccountID, Loan.REJECTED);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					sort();
				});
			}

			extra += 60;
		}
	}

	private void configureSortAndSearch() {
		controller.getFirstCMB().getItems().add("-");
		controller.getFirstCMB().getItems().add("Value");

		/*if (currentUser.AccessLevel == User.EMPLOYEE || currentUser.AccessLevel == User.MANAGER) {
			controller.getFirstCMB().getItems().add("ID");
		}*/
		controller.getFirstCMB().getSelectionModel().selectFirst();

		controller.getSecondCMB().getItems().add("-");
		controller.getSecondCMB().getItems().add("ASC");
		controller.getSecondCMB().getItems().add("DESC");
		controller.getSecondCMB().getSelectionModel().selectFirst();

		controller.getThirdCMB().getItems().add("All");
		controller.getThirdCMB().getItems().add("Pending");
		controller.getThirdCMB().getItems().add("Paying");
		controller.getThirdCMB().getItems().add("Finished");
		controller.getThirdCMB().getItems().add("Rejected");
		controller.getThirdCMB().getSelectionModel().selectFirst();

		controller.getSortBTN().setOnAction(e -> {
			sort();

			search();
		});

		controller.getSearchBTN().setOnMouseClicked(e -> {
			search();
		});
	}

	private void sort() {
		String statement = "Select * From Loan";

		switch (controller.getThirdCMB().getValue()) {
		case "Pending":
			statement += " Where Status=" + Loan.PENDING;
			break;
		case "Rejected":
			statement += " Where Status=" + Loan.REJECTED;
			break;
		case "Paying":
			statement += " Where Status=" + Loan.PAYING;
			break;
		case "Finished":
			statement += " Where Status=" + Loan.FINISHED;
			break;
		}

		if (currentUser.AccessLevel == User.CLIENT) {
			if (statement.contains("Where")) {
				statement += " AND OwnerID=\'" + currentUser.ID + "\'";
			} else {
				statement += " Where OwnerID=\'" + currentUser.ID + "\'";
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
			allLoans = UserController.ConvertLoansToArrayList(DBConnector.runCommand(statement));
			addAllLoans();
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Check Your Internet Connection !");
			alert.show();
		}
	}

	private void search() {
		String search = controller.getSearchTXF().getText();

		loansBoard.getChildren().clear();

		extra = 0;
		int index = 0;

		for (int i = 0; i < allLoans.size(); i++) {
			Loan loan = allLoans.get(i);

			if (loan.AccountID.contains(search) || loan.OwnerID.contains(search)
					|| Long.toString(loan.Value).contains(search)) {
				try {
					addLoan(index++, allLoans.get(i));
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
			loansBoard.getChildren().add(
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