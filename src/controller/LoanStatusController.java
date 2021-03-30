package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.DBConnector;
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
			System.out.println(UserController.getCurrentUser().getLoans().size());
			allUsers = DBConnector.getAllUsers();
			if (currentUser.AccessLevel == User.CLIENT) {
				loanCreator(currentUser);
			} else if (currentUser.AccessLevel == User.EMPLOYEE || currentUser.AccessLevel == User.MANAGER) {
				allLoansCreator();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	private void loanCreator(User u) throws Exception {
		for (int i = 0; i < u.getLoans().size(); i++) {
			System.out.println(u.getLoans().get(i));
			AnchorPane pane = null;
			System.out.println(u.getLoans().get(i).Status);
			switch (u.getLoans().get(i).Status) {
			case Loan.PENDING:
				pane = FXMLLoader.load(this.getClass().getResource("../view/components/pendingLoanAnchor.fxml"));
				break;
			case Loan.PAYING:
				pane = FXMLLoader.load(this.getClass().getResource("../view/components/acceptedLoanAnchor.fxml"));
				break;
			case Loan.REJECTED:
				pane = FXMLLoader.load(this.getClass().getResource("../view/components/rejectedLoanAnchor.fxml"));
				break;
			case Loan.FINISHED:
				pane = FXMLLoader.load(this.getClass().getResource("../view/components/finishedLoanAnchor.fxml"));
				break;

			}
			Group group = (Group) pane.getChildren().get(1);
			Label value = (Label) group.getChildren().get(1);
			Label id = (Label) group.getChildren().get(3);
			value.setText(String.valueOf(u.getLoans().get(i).Value));
			System.out.println(u.getLoans().get(i).Value);
			id.setText(u.getLoans().get(i).OwnerID);
			AnchorPane.setTopAnchor(pane, (double) (i * 80));
			loansBoard.getChildren().add(pane);
		}

	}

	private void allLoansCreator() throws Exception {
		for (User u : allUsers) {
			loanCreator(u);
		}
	}
}
