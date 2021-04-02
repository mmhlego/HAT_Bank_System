package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DBConnector;
import model.Sender;
import model.User;
import model.UserController;
import model.encoder;

public class Register implements Initializable {
    @FXML
    private Label title;

    @FXML
    private Button signUp;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField codeField;

    @FXML
    private TextField birthField;

    @FXML
    private JFXDatePicker birthPicker;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea addressField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        addressField.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
        focus(firstNameField);
        focus(lastNameField);
        focus(codeField);
        focus(birthField);
        focus(usernameField);
        focus(passwordField);
        focus(phoneField);
        addressField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
                    Boolean newPropertyValue) {
                if (newPropertyValue) {
                    addressField.setStyle(
                            "-fx-background-color:white;-fx-border-width:2px;-fx-border-color: #999e9d;-fx-border-radius: 200 200 200 200;");
                } else {
                    addressField.setStyle(
                            "-fx-background-color:#EEF5F3;-fx-border-width:0px;-fx-background-radius: 200 200 200 200;");
                }
            }
        });

        birthPicker.setOnAction(e -> {
            LocalDate date = birthPicker.getValue();
            birthField.setText(
                    String.format("%s / %s / %s ", date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        });

        signUp.setOnAction(e -> {
            try {
                if (DBConnector.UserExist(usernameField.getText())) {
                    Alert Error = new Alert(AlertType.ERROR);
                    Error.setContentText("This Username Already Exists Choose Another One");
                    Error.setHeaderText("Error");
                    Error.show();

                } else {
                    DBConnector.connect();
                    DBConnector.addUser(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                            encoder.encode(passwordField.getText()), emailField.getText(), phoneField.getText(), 0,
                            addressField.getText(), User.generateID(User.CLIENT), codeField.getText(),
                            birthPicker.getValue(), 0, 0);

                    UserController.setCurrentUser(DBConnector.getUser(usernameField.getText()));

                    try {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/MainStructure.fxml"));
                        Parent UserMainPage = loader.load();

                        ((Stage) title.getScene().getWindow()).close();

                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        Scene scene = new Scene(UserMainPage);
                        scene.setFill(Color.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception er) {
                        er.printStackTrace();
                    }

                    String pass = UserController.getCurrentUser().Password;

                    UserController.getCurrentUser().Password = passwordField.getText();
                    Sender.SendEmail(emailField.getText(), "Registration Was Successful !", Sender.Signupmail);

                    UserController.getCurrentUser().Password = pass;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }

    private void focus(TextField field) {
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
                    Boolean newPropertyValue) {
                if (newPropertyValue) {
                    field.setStyle(
                            "-fx-background-color:white;-fx-border-width:2px;-fx-border-color: #999e9d;-fx-border-radius: 200 200 200 200;");
                } else {
                    field.setStyle(
                            "-fx-background-color:#EEF5F3;-fx-border-width:0px;-fx-background-radius: 200 200 200 200;");
                }
            }
        });
    }
}