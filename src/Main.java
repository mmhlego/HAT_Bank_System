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
			// FXMLLoader loader = new
			// FXMLLoader(this.getClass().getResource("view/MainStructure.fxml"));
		//	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/withdrawPage.fxml"));
			 FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));
			Parent root = loader.load();
			// controller = loader.getController();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			DBConnector.setStage(primaryStage);

			/*DBConnector.setOffsetLeft(0.0);
			DBConnector.showLoading();
			Thread thread = new Thread(this);
			thread.start();*/

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
