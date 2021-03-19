import controller.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import model.DBConnector;
import model.Sender;
import model.UserController;

public class Main extends Application implements Runnable {
	public firstLoginController controller;

	public static void main(String[] args) throws Exception {
		launch(args);
		//System.out.println(DBConnector.checkUser("mmhlego","bab8f06012c8bd59f3e79b36b559c648574f13608a45e0644e1503d1eb76847a", 1));
		// Sender.SendEmail("mmhlegoautosmssender@gmail.com");
		//Sender.SendEmail("Recieve.tester@hi2.in", "Confirm Email", "k.tabani82@gmail.com");

		Sender.Load();
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			//FXMLLoader loading = new FXMLLoader(new File("src\\view\\DatabaseLoadingOverlay.fxml").toURI().toURL());

<<<<<<< HEAD
			// FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainStructure.fxml"));
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));
=======
			DBConnector.connect();
			UserController.setCurrentUser(DBConnector.getUser("mmhlego"));
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainStructure.fxml"));

			//FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));
>>>>>>> d220e4e7a734b1c893e4ac99d999413c94501400
			controller = loader.getController();

			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			StructureController.addButton(StructureController.CLIENTACCOUNTS);
			StructureController.addButton(StructureController.SETTINGS);

			/*DBConnector.setStage(primaryStage);
			
			DBConnector.setOffsetLeft(0.0);
			DBConnector.showLoading();
			Thread thread = new Thread(this);
			thread.start();*/
		} catch (Exception e) {
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
