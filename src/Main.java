
import controller.firstLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public firstLoginController controller;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));

			Parent root = loader.load();
			controller = loader.getController();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
		//System.out.println(DBConnector.checkUser("mmhlego","bab8f06012c8bd59f3e79b36b559c648574f13608a45e0644e1503d1eb76847a", 1));
	}
}
