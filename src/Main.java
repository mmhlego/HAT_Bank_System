import controller.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import model.*;

public class Main extends Application implements Runnable {
	public firstLoginController controller;

	private Stage stage;

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.initStyle(StageStyle.UNDECORATED);
		startProgram();
	}

	@Override
	public void run() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (!DBConnector.connect()) {
						closeAlert();
					}
				} catch (Exception e) {
					closeAlert();
				}
				DBConnector.closeLoading();
			}
		});
	}

	private void closeProgram() {
		stage.close();
	}

	private void startProgram() {
		try {
			//System.out.println(DBConnector.connect());

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));

			//UserController.setCurrentUser(DBConnector.getUser("Client3"));
			//FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainStructure.fxml"));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

			DBConnector.setStage(stage);
			DBConnector.setOffsetLeft(0.0);
			DBConnector.showLoading();
			Thread thread = new Thread(this);
			thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeAlert() {
		Alert a = new Alert(AlertType.CONFIRMATION);

		a.setTitle("ERROR");
		a.setContentText("An error accured while trying to connect to database.");
		a.initStyle(StageStyle.UNDECORATED);
		a.show();

		Button ok = (Button) a.getDialogPane().lookupButton(ButtonType.OK);
		Button cancel = (Button) a.getDialogPane().lookupButton(ButtonType.CANCEL);
		ok.setText("Retry");
		cancel.setText("Close");

		ok.setOnAction(e -> {
			closeProgram();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			startProgram();
		});

		cancel.setOnAction(e -> {
			System.exit(0);
		});
	}
}
