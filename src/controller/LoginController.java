package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import model.DBConnector;
import model.Sender;
import model.User;
import model.UserController;
import model.encoder;

public class LoginController implements Initializable, Runnable {

	private int duration = 1400;
	@FXML
	private Label titleText;
	@FXML
	private Button signButton;
	@FXML
	private JFXTextArea description;
	@FXML
	private Label title;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Button loginButton;
	@FXML
	private AnchorPane mainAnchor;
	@FXML
	private AnchorPane sideAnchor;
	@FXML
	private ImageView pic;
	@FXML
	private Group miniGroup;
	@FXML
	private Group exitGroup;
	FXMLLoader loader;
	FXMLLoader MainLoader;
	Parent UserMainPage;
	MainPageController controller;
	int AccessLevel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitGroup.setCursor(Cursor.HAND);
		exitGroup.setOnMouseClicked(e -> Platform.exit());
		miniGroup.setCursor(Cursor.HAND);
		miniGroup.setOnMouseClicked(e -> ((Stage) miniGroup.getScene().getWindow()).setIconified(true));

		loginButton.setOnAction(e -> login());
		loginButton.getParent().setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				login();
			}
		});

		// StructureController.addButton();

		loader = new FXMLLoader(this.getClass().getResource("../view/RegisterPage.fxml"));

		usernameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					usernameField.setStyle(
							"-fx-background-color:white;-fx-border-width:2px;-fx-border-color: #999e9d;-fx-border-radius: 200 200 200 200;");
				} else {
					usernameField.setStyle(
							"-fx-background-color:#EEF5F3;-fx-border-width:0px;-fx-background-radius: 200 200 200 200;");
				}
			}
		});
		passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					passwordField.setStyle(
							"-fx-background-color:white;-fx-border-width:2px;-fx-border-color: #999e9d;-fx-border-radius: 200 200 200 200;");
				} else {
					passwordField.setStyle(
							"-fx-background-color:#EEF5F3;-fx-border-width:0px;-fx-background-radius: 200 200 200 200;");
				}
			}
		});

	}

	private void login() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String hashPassword = encoder.encode(password);

		try {
			if (DBConnector.checkUser(username, hashPassword, AccessLevel)) {
				UserController.setCurrentUser(DBConnector.getUser(username));

				MainLoader = new FXMLLoader(this.getClass().getResource("../view/MainStructure.fxml"));
				try {
					UserMainPage = MainLoader.load();
					controller = new FXMLLoader(this.getClass().getResource("../view/mainPage.fxml")).getController();

					User currentuser = UserController.getCurrentUser();
					Sender.SendEmail(currentuser.Email, "Login Detected", Sender.Loginmail);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				((Stage) loginButton.getScene().getWindow()).close();
				Stage stage = new Stage(StageStyle.TRANSPARENT);
				Scene scene = new Scene(UserMainPage);
				scene.setFill(Color.TRANSPARENT);
				stage.setScene(scene);
				stage.show();
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Error");
				a.setContentText("Wrong username/password.");
				a.show();
			}
		} catch (Exception err) {
			DBConnector.closeLoading();
			err.printStackTrace();

			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setContentText(
					"An error accured whil trying to connect to database.\nPlease check your network connection.");
			a.show();
		}
	}

	AnchorPane register;

	public void changePage(int accessLevel) {
		AccessLevel = accessLevel;

		switch (accessLevel) {
		case User.MANAGER: {
			titleText.setText("Hi Sir!");
			description.setText("Have A Wonderful Day");
			title.setText("Manager Login");
			signButton.setVisible(false);
			try {
				pic.setImage(new Image(new FileInputStream(new File(
						"src\\view\\pictures\\project-management-body-of-knowledge-project-manager-executive-manager-businessmanatdesk-thumbnail-removebg-preview.png"))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
			break;
		case User.EMPLOYEE: {
			titleText.setText("Hi!");
			description.setText("Try To Do Your Best.");
			title.setText("Employee Login");
			signButton.setVisible(false);
			try {
				pic.setImage(
						new Image(new FileInputStream(new File("src/view/pictures/1869679-removebg-preview.png"))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
			break;
		case User.CLIENT: {
			try {
				register = loader.load();
				register.toFront();
				signButton.setOnAction(e -> {
					Thread thread = new Thread(this);
					thread.start();
					move();

				});

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			titleText.setText("New Here?");
			description.setText("Sign up and use our bank to discover a lot of features!");
			title.setText("Client Login");
			signButton.setVisible(true);
			try {
				pic.setImage(new Image(new FileInputStream(new File(
						"src/view/pictures/client-icon-businessman-icon-vector-design-removebg-preview.png"))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
			break;
		}
	}

	boolean right = true;

	private void move() {
		if (right) {
			sideAnchor.toFront();

			TranslateTransition translate1 = new TranslateTransition();
			translate1.setByX(600);
			translate1.setDuration(Duration.millis(duration));
			translate1.setNode(sideAnchor);
			translate1.setCycleCount(1);
			translate1.setAutoReverse(false);
			translate1.play();

			ScaleTransition scale1 = new ScaleTransition();
			scale1.setNode(sideAnchor);
			scale1.setToX(2);
			scale1.setCycleCount(1);
			scale1.setDuration(Duration.millis(duration / 2));

			ScaleTransition scale2 = new ScaleTransition();
			scale2.setNode(sideAnchor);
			scale2.setToX(1);
			scale2.setCycleCount(1);
			scale2.setDuration(Duration.millis(duration / 2));

			SequentialTransition transition = new SequentialTransition(scale1, scale2);
			transition.play();

			TranslateTransition translate2 = new TranslateTransition();
			translate2.setByX(-300);
			translate2.setDuration(Duration.millis(duration));
			translate2.setNode(mainAnchor);
			translate2.setCycleCount(1);
			translate2.setAutoReverse(false);
			translate2.play();
			right = false;
		} else {
			sideAnchor.toFront();

			TranslateTransition translate1 = new TranslateTransition();
			translate1.setByX(-600);
			translate1.setDuration(Duration.millis(duration));
			translate1.setNode(sideAnchor);
			translate1.setCycleCount(1);
			translate1.setAutoReverse(false);
			translate1.play();

			ScaleTransition scale1 = new ScaleTransition();
			scale1.setNode(sideAnchor);
			scale1.setToX(2);
			scale1.setCycleCount(1);
			scale1.setDuration(Duration.millis(duration / 2));

			ScaleTransition scale2 = new ScaleTransition();
			scale2.setNode(sideAnchor);
			scale2.setToX(1);
			scale2.setCycleCount(1);
			scale2.setDuration(Duration.millis(duration / 2));

			SequentialTransition transition = new SequentialTransition(scale1, scale2);
			transition.play();

			TranslateTransition translate2 = new TranslateTransition();
			translate2.setByX(300);
			translate2.setDuration(Duration.millis(duration));
			translate2.setNode(mainAnchor);
			translate2.setCycleCount(1);
			translate2.setAutoReverse(false);
			translate2.play();
			right = true;
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(duration / 4);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					titleText.setVisible(false);
					description.setVisible(false);
					signButton.setVisible(false);
					if (signButton.getText().equals("Sign In")) {
						// mainGroup.getChildren().add(signInGroup);
						signButton.setText("Sign Up");
					} else {
						signButton.setText("Sign In");
					}

				}
			});
			Thread.sleep(duration / 4);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					if (!right) {
						mainAnchor.setStyle("-fx-background-color:white;-fx-background-radius:25 0 0 25;");
						sideAnchor.setStyle(sideAnchor.getStyle() + "-fx-background-radius:0 25 25 0;");
						register.toFront();
						register.setStyle("-fx-background-color:white;-fx-background-radius:25 0 0 25");
						mainAnchor.getChildren().add(register);
						register.toFront();
						titleText.setText("One Of Us?");
						description.setText("If you already have an account, just sign in. We've missed you!");

					} else {
						mainAnchor.setStyle("-fx-background-color:white;-fx-background-radius:0 25 25 0;");
						sideAnchor.setStyle(sideAnchor.getStyle() + "-fx-background-radius:25 0 0 25;");
						mainAnchor.getChildren().remove(register);
						titleText.setText("New Here?");
						description.setText("Sign up and use our bank to discover a lot of features!");
					}

				}
			});
			Thread.sleep(duration / 4);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					titleText.setVisible(true);
					description.setVisible(true);
					signButton.setVisible(true);

				}
			});
			Thread.sleep(duration / 4);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
