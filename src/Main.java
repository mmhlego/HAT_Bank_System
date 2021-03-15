import controller.firstLoginController;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
<<<<<<< HEAD
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;

public class Main extends Application{
=======
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import model.DBConnector;

public class Main extends Application implements Runnable {
>>>>>>> 77843b4ff0029818b48b8f21e74844f78f9401fc
	public firstLoginController controller;

	@Override
    public void start(Stage primaryStage) {
        
		try {
<<<<<<< HEAD
            FXMLLoader loader = new FXMLLoader(new File("src/view/firstPage.fxml").toURI().toURL());
            
			Parent root = loader.load();
		    controller = loader.getController();
=======
			//FXMLLoader loading = new FXMLLoader(new File("src\\view\\DatabaseLoadingOverlay.fxml").toURI().toURL());

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));
			controller = loader.getController();

			Parent root = loader.load();
>>>>>>> 77843b4ff0029818b48b8f21e74844f78f9401fc
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

    public static void main(String[] args) {
        launch(args);
		//System.out.println(DBConnector.checkUser("mmhlego","bab8f06012c8bd59f3e79b36b559c648574f13608a45e0644e1503d1eb76847a", 1));
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
					e.printStackTrace();
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
