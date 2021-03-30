package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DBConnector;
import model.User;
import model.UserController;

public class LoanStatusController implements Initializable {

	ObservableList<String> ComboValues = FXCollections.observableArrayList("Date", "Account ID", "Value");
	@FXML
	private AnchorPane MainPanel;
	@FXML
	private Group pendingGroup;
	@FXML
	private VBox pendingBox;
	@FXML
	private AnchorPane pendingAnchor;
	@FXML
	private Label pendingLoanValue;
	@FXML
	private Label pendingLoanID;
	@FXML
	private Group acceptedGroup;
	@FXML
	private VBox acceptedBox;
	@FXML
	private AnchorPane accceptedAnchor;
	@FXML
	private Label acceptedLoanValue;
	@FXML
	private Label acceptedLoanID;
	@FXML
	private Group finishedGroup;
	@FXML
	private VBox finishedBox;
	@FXML
	private AnchorPane finishedAnchor;
	@FXML
	private Label finishedLoanValue;
	@FXML
	private Label finishedLoanID;
	@FXML
	private Group rejectedGroup;
	@FXML
	private VBox rejectedBox;
	@FXML
	private AnchorPane rejectedAnchor;
	@FXML
	private Label rejectedLoanValue;
	@FXML
	private Label rejectedLoanID;
	@FXML
	private TextField SearchTF;
	@FXML
	private ImageView Searchbtn;
	@FXML
	private RadioButton ASCRB;
	@FXML
	private RadioButton DESCRB;
	@FXML
	private JFXComboBox<?> SortOptionCB;
	User currentUser;
	ArrayList<User> allUsers;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			currentUser = UserController.getCurrentUser();
			allUsers = DBConnector.getAllUsers();
			pendingBox.getChildren().clear();
			acceptedBox.getChildren().clear();
			finishedBox.getChildren().clear();
			rejectedBox.getChildren().clear();
			if (currentUser.AccessLevel == 2) {
				ClientAcceptedLoansCreator(currentUser);
				ClientFinishedLoansCreator(currentUser);
				ClientPendingLoansCreator(currentUser);
				ClientRejectedLoansCreator(currentUser);

			} else if (currentUser.AccessLevel == 0 || currentUser.AccessLevel == 1) {
				allLoansCreator();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Searchbtn.setOnMouseClicked((e) -> {

		});
	}

	private void ClientPendingLoansCreator(User u) throws Exception {
		for (int i = 0; i < currentUser.Loans.size(); i++) {
			AnchorPane pane = FXMLLoader.load(this.getClass().getResource("../view/components/pendingLoanAnchor.fxml"));
			Label value = (Label) pane.getChildren().get(1);
			Label id = (Label) pane.getChildren().get(3);
			if (currentUser.Loans.get(i).Status == 0) {
				value.setText(String.valueOf(currentUser.Loans.get(i).Value));
				id.setText(currentUser.Loans.get(i).OwnerID);
				pendingBox.getChildren().add(pane);
			}
		}
	}

	private void ClientAcceptedLoansCreator(User u) throws Exception {
		for (int i = 0; i < currentUser.Loans.size(); i++) {
			AnchorPane pane = FXMLLoader
					.load(this.getClass().getResource("../view/components/acceptedLoanAnchor.fxml"));
			Label value = (Label) pane.getChildren().get(1);
			Label id = (Label) pane.getChildren().get(3);
			if (currentUser.Loans.get(i).Status == 2) {
				value.setText(String.valueOf(currentUser.Loans.get(i).Value));
				id.setText(currentUser.Loans.get(i).OwnerID);
				pendingBox.getChildren().add(pane);
			}
		}
	}

	private void ClientFinishedLoansCreator(User u) throws Exception {
		for (int i = 0; i < currentUser.Loans.size(); i++) {
			AnchorPane pane = FXMLLoader
					.load(this.getClass().getResource("../view/components/finishedLoanAnchor.fxml"));
			Label value = (Label) pane.getChildren().get(1);
			Label id = (Label) pane.getChildren().get(3);
			if (currentUser.Loans.get(i).Status == 3) {
				value.setText(String.valueOf(currentUser.Loans.get(i).Value));
				id.setText(currentUser.Loans.get(i).OwnerID);
				pendingBox.getChildren().add(pane);
			}
		}
	}

	private void ClientRejectedLoansCreator(User u) throws Exception {
		for (int i = 0; i < currentUser.Loans.size(); i++) {
			AnchorPane pane = FXMLLoader
					.load(this.getClass().getResource("../view/components/rejectedLoanAnchor.fxml"));
			Label value = (Label) pane.getChildren().get(1);
			Label id = (Label) pane.getChildren().get(3);
			if (currentUser.Loans.get(i).Status == 1) {
				value.setText(String.valueOf(currentUser.Loans.get(i).Value));
				id.setText(currentUser.Loans.get(i).OwnerID);
				pendingBox.getChildren().add(pane);
			}
		}
	}

	private void allLoansCreator() throws Exception {
		for (User u : allUsers) {
			ClientAcceptedLoansCreator(u);
			ClientFinishedLoansCreator(u);
			ClientPendingLoansCreator(u);
			ClientRejectedLoansCreator(u);
		}
	}
}
