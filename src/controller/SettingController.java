package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.DBConnector;
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
        User currentUser = UserController.getCurrentUser();
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

        changePasswordTXf.setOnAction((e) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/changePassword.fxml"));
            try {
                MainPanel.getChildren().add(loader.load());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @FXML
    void ChangeSave(ActionEvent event) {
        User currentUser = UserController.getCurrentUser();
        if (changeInformationBTN.getText().equals("Change Information")) {
            changeInformationBTN.setText("Save Information");
            firstNameTXF.setEditable(true);
            lastNameTXF.setEditable(true);
            phoneTXF.setEditable(true);
            addressTXA.setEditable(true);
        } else {
            User u = new User(firstNameTXF.getText(), lastNameTXF.getText(), currentUser.Username, currentUser.Password,
                    currentUser.AccessLevel, addressTXA.getText(), currentUser.ID, currentUser.NationalCode,
                    currentUser.BirthDate, currentUser.Email, phoneTXF.getText(), currentUser.Theme,
                    currentUser.Language);
            try {
                DBConnector.UpdateUser(u);
                UserController.updatePersonalData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}