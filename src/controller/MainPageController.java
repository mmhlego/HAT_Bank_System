package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private VBox img;

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

    @FXML
    private Label topLabel;

    @FXML
    private VBox topVbox;

    @FXML
    private Label bottomLabel;

    @FXML
    private VBox bottomVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = UserController.getCurrentUser();
        firstNameLBL.setText(currentUser.FirstName);
        lastNameLBL.setText(currentUser.LastName);
        idLBL.setText(currentUser.ID);
        usenameLBL.setText(currentUser.Username);
        phoneLBL.setText(currentUser.PhoneNumber);

    }

}
