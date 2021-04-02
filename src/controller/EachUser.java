package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class EachUser {

    @FXML
    private AnchorPane eachUser;

    @FXML
    private Label role;

    @FXML
    private ImageView image;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label usename;

    @FXML
    private Label nationalCode;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label id;

	public AnchorPane getEachUser() {
		return eachUser;
	}

	public void setEachUser(AnchorPane eachUser) {
		this.eachUser = eachUser;
	}

	public Label getRole() {
		return role;
	}

	public void setRole(Label role) {
		this.role = role;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public Label getFirstName() {
		return firstName;
	}

	public void setFirstName(Label firstName) {
		this.firstName = firstName;
	}

	public Label getLastName() {
		return lastName;
	}

	public void setLastName(Label lastName) {
		this.lastName = lastName;
	}

	public Label getUsename() {
		return usename;
	}

	public void setUsename(Label usename) {
		this.usename = usename;
	}

	public Label getNationalCode() {
		return nationalCode;
	}

	public void setNationalCode(Label nationalCode) {
		this.nationalCode = nationalCode;
	}

	public Label getPhone() {
		return phone;
	}

	public void setPhone(Label phone) {
		this.phone = phone;
	}

	public Label getEmail() {
		return email;
	}

	public void setEmail(Label email) {
		this.email = email;
	}

	public Label getId() {
		return id;
	}

	public void setId(Label id) {
		this.id = id;
	}

}
