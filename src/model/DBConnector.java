package model;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DBConnector {

    private static Connection con;
    private static Stage stage;
    private static ResultSet results;
    private static double offsetLeft = 0.0;

    // =============================================================================================
    // GETTERS & SETTERS

    public static void setOffsetLeft(Double d) {
        offsetLeft = d;
    }

    public static void setStage(Stage st) {
        stage = st;
    }

    /*private static ResultSet getResults() {
        return results;
    }*/

    // =============================================================================================
    // LOADING SCREEN METHODS

    public static boolean showLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src\\view\\DatabaseLoadingOverlay.fxml").toURI().toURL());
            AnchorPane root = loader.load();

            AnchorPane ap = ((AnchorPane) stage.getScene().getRoot());
            ap.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 26.0);
            AnchorPane.setLeftAnchor(root, offsetLeft);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void closeLoading() {
        AnchorPane ap = ((AnchorPane) stage.getScene().getRoot());
        int len = ap.getChildren().size();
        ap.getChildren().remove(len - 1);
    }

    // =============================================================================================
    // CONNECTION METHODS

    public static boolean connect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://freedb.tech/freedbtech_hatbanksystem", "freedbtech_hatuser",
                "hatpassword");

        if (con != null) {
            return true;
        }
        return false;
    }

    /*private static void closeConnection() throws Exception {
        con.close();
    }*/

    // =============================================================================================
    // COMPLETE METHODS

    private static ResultSet runCommand(String command) throws Exception {
        results = null;

        Statement stmt = con.createStatement();
        results = stmt.executeQuery(command);

        //con.createStatement().executeUpdate(command);

        return results;
    }

    public static boolean checkUser(String username, String hashPassword, int AccessLevel) throws Exception {
        runCommand("select * from User WHERE Username=\'" + username + "\' AND Password=\'" + hashPassword
                + "\' AND AccessLevel=" + AccessLevel);

        if (results == null) {
            return false;
        } else if (results.next()) {
            return true;
        }
        return false;
    }

    public static boolean UserExist(String Username) throws Exception {
        runCommand("select * from User WHERE Username =\'" + Username + "\'");
        if (results == null) {
            return false;
        } else if (results.next()) {
            return true;
        }
        return false;
    }

    // ============================================================================================= Insert methods

    public static void addUser(String firstname, String lastname, String Username, String Password, String Email,
            String Phone, int AccessLevel, String address, String id, String nationalCode, LocalDate birthDate,
            int theme, int language) throws Exception {

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `User`(`FirstName`, `LastName`, `Username`, `Password`, `AccessLevel`, `Address`, `ID`, `National Code`, `BirthDate`, `Email`, `PhoneNumber`, `Theme`, `Language`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

        ps.setString(1, firstname);
        ps.setString(2, lastname);
        ps.setString(3, Username);
        ps.setString(4, Password);
        ps.setString(5, Integer.toString(AccessLevel));
        ps.setString(6, address);
        ps.setString(7, id);
        ps.setString(8, nationalCode);
        ps.setDate(9, Date.valueOf(birthDate));
        ps.setString(10, Email);
        ps.setString(11, Phone);
        ps.setString(12, Integer.toString(theme));
        ps.setString(13, Integer.toString(language));

        ps.executeUpdate();
    }

    public static int numberOfUsers(int accessLevel) {
        int count = 0;
        try {
            ResultSet r = runCommand("Select * from User where AccessLevel=" + accessLevel);

            while (r.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /* public static void addLoan(String OwnerID, String accountID, int status, long value, int percentage, long totalPay,
            long payed, Date dueDate, String guarantorid) throws Exception {
        runCommand(
                "INSERT INTO Loan (FirstName LastName Username Password AccessLevel Address ID NationalCode BirthDate) Values (\'"
                        + firstname + "\'" + "\'" + lastname + "\'" + "\'" + Username + "\'" + "\'" + Password + "\'"
                        + AccessLevel + "\'" + address + "\'" + id + "\'" + nationalCode + "\'" + birthDate);
    }*/

    // =============================================================================================

    public static ResultSet getLoans(String OwnerID) throws Exception {
        return runCommand("select * from Loan where OwnerID=\'" + OwnerID + "\'");
    }

    public static ResultSet getAccounts(String OwnerID) throws Exception {
        return runCommand("select * from Account where OwnerID=\'" + OwnerID + "\'");
    }

    /*public static ResultSet getTransactions(String OwnerID) throws Exception {
        return runCommand("select * from Transaction where OwnerID=\'" + OwnerID + "\'");
    }*/

    public static User getUser(String username) {
        try {
            ResultSet r = runCommand("select * from User where Username=\'" + username + "\'");

            if (r.next()) {

                return (new User(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
                        r.getString(6), r.getInt(7), r.getString(8), r.getString(9), r.getString(10),
                        r.getDate(11).toLocalDate(), r.getInt(12), r.getInt(13)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * private static void connect() throws Exception {
     * Class.forName("com.mysql.jdbc.Driver"); con = DriverManager.getConnection(
     * "jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6398536", "sql6398536",
     * "t8YnlV3Mzt");
     * 
     * Statement stmt = con.createStatement(); ResultSet rs =
     * stmt.executeQuery("select * from Sample"); while (rs.next()) {
     * System.out.println(rs.getString(1)); } }
     */
}