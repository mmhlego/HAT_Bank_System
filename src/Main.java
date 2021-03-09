
import controller.firstLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public firstLoginController controller ;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/firstPage.fxml"));
			
			Parent root = loader.load();
			controller =loader.getController();
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
		//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
