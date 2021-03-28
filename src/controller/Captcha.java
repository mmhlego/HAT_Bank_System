package controller;

import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Captcha implements Initializable {

	@FXML
	private HBox hbox;

	@FXML
	private Pane pane1;

	@FXML
	private Label text1;

	@FXML
	private Pane pane2;

	@FXML
	private Label text2;

	@FXML
	private Pane pane3;

	@FXML
	private Label text3;

	@FXML
	private Pane pane4;

	@FXML
	private Label text4;

	@FXML
	private Pane pane5;

	@FXML
	private Label text5;

	@FXML
	private Pane pane6;

	@FXML
	private Label text6;

	@FXML
	private AnchorPane root;

	String captchaResult;

	public String getCaptchaResult() {
		return captchaResult;
	}

	public void setCaptchaResult(String captchaResult) {
		this.captchaResult = captchaResult;
	}

	Random r;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		r = new Random();
		String chars = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,1,2,3,4,5,6,7,8,9,0,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		StringTokenizer tokenizer = new StringTokenizer(chars, ",");
		int i = 0;
		String[] cs = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreElements()) {
			cs[i] = (String) tokenizer.nextElement();
			i++;
		}
		String t1 = cs[r.nextInt(cs.length)];
		String t2 = cs[r.nextInt(cs.length)];
		String t3 = cs[r.nextInt(cs.length)];
		String t4 = cs[r.nextInt(cs.length)];
		String t5 = cs[r.nextInt(cs.length)];
		String t6 = cs[r.nextInt(cs.length)];
		setCaptchaResult(t1 + t2 + t3 + t4 + t5 + t6);

		text1.setText(t1);
		text2.setText(t2);
		text3.setText(t3);
		text4.setText(t4);
		text5.setText(t5);
		text6.setText(t6);

		getField(text1);
		getField(text2);
		getField(text3);
		getField(text4);
		getField(text5);
		getField(text6);

		drawings();

	}

	private void getField(Label t) {
		textRotate(t);
		textTranslate(t);

	}

	private void textRotate(Label t) {
		t.setRotate((r.nextDouble() * 90) - 45);
	}

	private void textTranslate(Label t) {
		//t.set
		t.setLayoutY(t.getLayoutY() + r.nextDouble() * (40 - t.getHeight() - 6) - (40 - t.getHeight() - 6) / 2);
		t.setLayoutX(t.getLayoutX() + r.nextDouble() * (50 - t.getWidth() - 6) - (50 - t.getWidth() - 6) / 2);
	}

	private void drawings() {
		for (int i = 0; i < r.nextInt(2) + 2; i++) {
			Line line = new Line();
			line.setStartX(r.nextDouble() * 300);
			line.setEndX(r.nextDouble() * 300);
			line.setStartY(r.nextDouble() * 40);
			line.setEndY(r.nextDouble() * 40);
			line.setStroke(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			root.getChildren().add(line);
			line.toFront();
		}
	}

}
