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

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			//System.out.println(DBConnector.connect());

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));

			//UserController.setCurrentUser(DBConnector.getUser("Client40"));
		//	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainStructure.fxml"));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			DBConnector.setStage(primaryStage);
			DBConnector.setOffsetLeft(0.0);
			DBConnector.showLoading();
			Thread thread = new Thread(this);
			thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (!DBConnector.connect()) {
						close();
					}
				} catch (Exception e) {
					close();
				}
				DBConnector.closeLoading();
			}
		});
	}

	public void close() {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("ERROR");
		a.setContentText("An error accured while trying to connect to database.");
		a.initStyle(StageStyle.UNDECORATED);
		a.show();

		Button ok = (Button) a.getDialogPane().lookupButton(ButtonType.OK);

		ok.setOnAction(e -> {
			System.exit(0);
		});
	}
}
