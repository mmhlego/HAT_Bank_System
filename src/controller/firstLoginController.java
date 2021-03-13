package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
public class firstLoginController implements Initializable{


    
	@FXML
    private AnchorPane firstPage;

    @FXML
    private Group ManagerLogin;

    @FXML
    private Group EmployeeLogin;

    @FXML
    private Group clientsLogin;

    @FXML
    private ToggleButton clientButton;
    
    @FXML
    private ToggleButton employeeButton;
    
    @FXML
    private ToggleButton managerButton;
    
    FXMLLoader loader;
    AnchorPane root;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loader=new FXMLLoader(this.getClass().getResource("../view/LoginPage.fxml"));
			root=loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LoginController controller = loader.getController();
		mouseOver(managerButton);
		mouseOver(employeeButton);
		mouseOver(clientButton);
		
		clientsLogin.setOnMouseClicked(e->{
			 changePage() ;
			controller.changePage(2);
		});
		
		clientButton.setOnAction(e->{
			 changePage() ;
			controller.changePage(2);
		});
		
		ManagerLogin.setOnMouseClicked(e->{
			 changePage();
			controller. changePage(0);
		});
		
		managerButton.setOnAction(e->{
			changePage();
			controller. changePage(0);
		});
		EmployeeLogin.setOnMouseClicked(e->{
			 changePage();
			controller.changePage(1);
		});
		
		employeeButton.setOnAction(e->{
			changePage();
			controller.changePage(1);
		});
			
	}
	int i;
	
	private void mouseOver(ToggleButton button) {

		button.getParent().setOnMouseEntered(e->{
			
			//button.setStyle("-fx-background-color: linear-gradient(to bottom right,#ff8877,#de1dbf); -fx-background-radius: 25 25 25 25; ");
		    button.setStyle("-fx-background-color: linear-gradient(to bottom right,#0099bb,#3bffad); -fx-background-radius: 25 25 25 25; ");
			button.setTextFill(Color.WHITE);
			
		});
		
		button.getParent().setOnMouseExited(e->{
			button.setStyle("-fx-background-color: white; -fx-background-radius: 25 25 25 25; ");
			button.setTextFill(Color.BLACK);
		});
		
	}

	private void changePage() {
		
		TranslateTransition transition = new TranslateTransition();
		transition.setByX(-1000);
		transition.setInterpolator(Interpolator.EASE_OUT);
		transition.setNode(firstPage);
		transition.setAutoReverse(false);
		transition.setDuration(Duration.seconds(4));
		transition.setCycleCount(1);
		transition.play();
		firstPage.setPrefWidth(2000);
		root.relocate(1000, 0);
		firstPage.getChildren().add(root);
	
		
	}

}
