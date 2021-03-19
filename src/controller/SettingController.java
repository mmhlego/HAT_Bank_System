package controller;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.User;
import model.UserController;

public class SettingController implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private JFXTextField firstNameTXF;

    @FXML
    private JFXTextField lastNameTXF;

    @FXML
    private JFXTextField nationalCodeTXF;

    @FXML
    private JFXTextField phoneTXF;

    @FXML
    private JFXTextField birthTXF;

    @FXML
    private JFXTextArea addressTXA;

    @FXML
    private JFXButton changeInformationBTN;

    @FXML
    private JFXTextField usernameTXF;

    @FXML
    private JFXTextField roleTXF;

    @FXML
    private JFXButton changePasswordTXf;

    @FXML
    private JFXToggleButton languageToggle;

    @FXML
    private JFXToggleButton themeToggle;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	User currentUser=UserController.getCurrentUser();
    	firstNameTXF.setText(currentUser.FirstName);
    	lastNameTXF.setText(currentUser.LastName);
    	nationalCodeTXF.setText(currentUser.NationalCode);
    	phoneTXF.setText(currentUser.PhoneNumber);
    	birthTXF.setText(currentUser.BirthDate.toString());
    	usernameTXF.setText(currentUser.Username);
    	switch (currentUser.AccessLevel) {
		case 0:
			roleTXF.setText("Client");
			break;
		case 1:
			roleTXF.setText("Employee");
			break;
		case 2:
			roleTXF.setText("Manager");
			break;

		}
    	addressTXA.setText(currentUser.Address);
    }
}