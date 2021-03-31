package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Loan;
import model.User;
import model.UserController;

public class LoanStatusController implements Initializable {

	ObservableList<String> ComboValues = FXCollections.observableArrayList("Date", "Account ID", "Value");
	@FXML
	private AnchorPane MainPanel;

	@FXML
	private AnchorPane loansBoard;
	User currentUser;
	ArrayList<User> allUsers;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/components/sortSearch.fxml"));
			Parent searchBox = loader.load();
			AnchorPane.setTopAnchor(searchBox, (double) 100);
			AnchorPane.setLeftAnchor(searchBox, (double) 50);
			MainPanel.getChildren().add(searchBox);

			currentUser = UserController.getCurrentUser();

			loanCreator();

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	int extra = 0;
	String currency = " Rials";

	private void loanCreator() throws Exception {

		if (UserController.getLoans().size() == 0) {

		}

		for (int i = 0; i < UserController.getLoans().size(); i++) {
			Loan loan = UserController.getLoans().get(i);
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

			addLoan(pane, i, loan);
		}
	}

	private void addLoan(AnchorPane pane, int index, Loan loan) {
		Group group = (Group) pane.getChildren().get(1);
		((Label) group.getChildren().get(1)).setText(String.valueOf(loan.Value) + currency);
		((Label) group.getChildren().get(3)).setText(loan.OwnerID);

		AnchorPane.setTopAnchor(pane, (double) (index * 80 + extra));
		loansBoard.getChildren().add(pane);

		if (loan.Status == Loan.PAYING) {
			double percent = (double) loan.Payed / (double) loan.TotalPay;

			((Label) group.getChildren().get(5)).setText(Long.toString(loan.Payed) + currency);
			((JFXProgressBar) pane.getChildren().get(2)).setProgress(percent);
			((Label) pane.getChildren().get(3)).setText(String.format("%,.1f", percent * 100) + "%");

			if (percent < 0.5) {
				((Label) pane.getChildren().get(3)).setStyle("-fx-text-fill: #000;");
			}

			if (currentUser.AccessLevel == User.MANAGER || currentUser.AccessLevel == User.EMPLOYEE) {
				group.getChildren().get(6).setVisible(false);
			}

			extra += 120;
		} else if (loan.Status == Loan.PENDING) {
			if (currentUser.AccessLevel == User.CLIENT) {
				((Label) pane.getChildren().get(4)).setVisible(true);
			} else if (currentUser.AccessLevel == User.MANAGER || currentUser.AccessLevel == User.EMPLOYEE) {
				((JFXButton) pane.getChildren().get(2)).setVisible(true);
				((JFXButton) pane.getChildren().get(2)).setOnAction(e -> {

					System.out.println("accept" + index);

				});

				((JFXButton) pane.getChildren().get(3)).setVisible(true);
				((JFXButton) pane.getChildren().get(3)).setOnAction(e -> {

					System.out.println("reject" + index);

				});
			}

			extra += 60;
		}
	}
}