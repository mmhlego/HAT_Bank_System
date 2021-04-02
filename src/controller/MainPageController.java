package controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.User;
import model.UserController;

public class MainPageController implements Initializable {
	@FXML
	private AnchorPane MainPanel;
	@FXML
	private AnchorPane rightPanel;
	@FXML
	private ImageView img;
	@FXML
	private VBox rightVbox;
	@FXML
	private Label idLBL;
	@FXML
	private Label firstNameLBL;
	@FXML
	private Label lastNameLBL;
	@FXML
	private Label usenameLBL;
	@FXML
	private Label phoneLBL;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User currentUser = UserController.getCurrentUser();
		// img set 
		System.out.println(currentUser);
		firstNameLBL.setText("First Name: " + currentUser.FirstName);
		lastNameLBL.setText("Last Name: " + currentUser.LastName);
		idLBL.setText("ID: " + currentUser.ID);
		usenameLBL.setText("Username: " + currentUser.Username);
		phoneLBL.setText("Phone: " + currentUser.PhoneNumber);
		try {
			switch (currentUser.AccessLevel) {
			case User.MANAGER:
				img.setImage(new Image(new FileInputStream(new File(
						"src\\view\\pictures\\project-management-body-of-knowledge-project-manager-executive-manager-businessmanatdesk-thumbnail-removebg-preview.png"))));
				break;
			case User.EMPLOYEE:
				img.setImage(
						new Image(new FileInputStream(new File("src/view/pictures/1869679-removebg-preview.png"))));
				break;
			case User.CLIENT:
				img.setImage(new Image(new FileInputStream(new File(
						"src/view/pictures/client-icon-businessman-icon-vector-design-removebg-preview.png"))));
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
