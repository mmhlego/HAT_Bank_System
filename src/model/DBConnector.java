package model;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jfoenix.controls.JFXPasswordField;

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

    /*
     * private static ResultSet getResults() { return results; }
     */

    // =============================================================================================
    // LOADING SCREEN METHODS

    public static boolean showLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src/view/DatabaseLoadingOverlay.fxml").toURI().toURL());
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

    /*
     * private static void closeConnection() throws Exception { con.close(); }
     */

    // =============================================================================================
    // COMPLETE METHODS

    public static ResultSet runCommand(String command) throws Exception {
        results = null;

        Statement stmt = con.createStatement();
        results = stmt.executeQuery(command);

        // con.createStatement().executeUpdate(command);

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

    public static boolean IsMoneyEnough(long Value, String BIC) {
        try {
            ResultSet r = runCommand("SELECT Value from Account WHERE BIC=\'" + BIC + "\'");
            r.next();
            long currentvalue = r.getLong(1);
            if (Value <= currentvalue) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void UpdateUser(User u) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE User Set FirstName=\'" + u.FirstName + "\' , Lastname=\'"
                + u.LastName + "\' , Address=\'" + u.Address + "\' , Email=\'" + u.Email + "\' , PhoneNumber=\'"
                + u.PhoneNumber + "\' WHERE ID=\'" + u.ID + "\'");
        ps.executeUpdate();
    }

    public static void UpdatePassword(User u) throws Exception {
        PreparedStatement ps = con
                .prepareStatement("UPDATE User Set Password=\'" + u.Password + "\' WHERE ID=\'" + u.ID + "\'");
        ps.executeUpdate();
    }

    public static void UpdateCVV(String BIC, String NewCVV) throws Exception {
        PreparedStatement ps = con
                .prepareStatement("UPDATE Account Set CVV=\'" + NewCVV + "\' WHERE BIC=\'" + BIC + "\'");
        ps.executeUpdate();
    }

    public static void UpdateAccount(Account a) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE Account Set CVV=\'" + a.CVV + "\' , Value=" + a.Value
                + " WHERE AccountID=\'" + a.AccountID + "\'");
        ps.executeUpdate();
    }

    public static boolean CheckCardInfo(String BIC, String CVV, String CVV2, int Year, int Month) {
        try {
            ResultSet r = runCommand("SELECT CVV , CVV2 , EXDate from Account WHERE BIC=\'" + BIC + "\'");
            r.next();
            String cvv = r.getString(1);
            String cvv2 = r.getString(2);
            int year = r.getDate(3).toLocalDate().getYear() % 100;
            int month = r.getDate(3).toLocalDate().getMonthValue();
            if (cvv.equals(CVV) && cvv2.equals(CVV2) && year == Year && month == Month) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean CheckTransferCardInfo(String BIC, String CVV2, int Year, int Month) {
        try {
            ResultSet r = runCommand("SELECT CVV2 , EXDate from Account WHERE BIC=\'" + BIC + "\'");
            r.next();
            String cvv2 = r.getString(1);
            int year = r.getDate(2).toLocalDate().getYear() % 100;
            int month = r.getDate(2).toLocalDate().getMonthValue();
            if (cvv2.equals(CVV2) && year == Year && month == Month) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean IsCardAlive(int Year, int Month) {
        int monthnow = LocalDate.now().getMonthValue();
        int yearnow = LocalDate.now().getYear() % 100;
        if (Year > yearnow) {
            return true;
        } else if (Year == yearnow && Month >= monthnow) {
            return true;
        }
        return false;
    }

    public static boolean IsDestinationCardAlive(String BIC) {
        try {
            ResultSet r = runCommand("Select ExDate from Account WHERE BIC=\'" + BIC + "\'");
            r.next();
            int year = r.getDate(1).toLocalDate().getYear() % 100;
            int month = r.getDate(1).toLocalDate().getMonthValue();
            if (IsCardAlive(year, month)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void changeValue(long value, String card) {
        try {
            ResultSet r = runCommand("SELECT Value from Account WHERE BIC=\'" + card + "\'");
            r.next();
            long currentvalue = r.getLong(1);
            long finalvalue = currentvalue + value;

            con.prepareStatement("UPDATE Account Set Value=" + finalvalue + " WHERE BIC=\'" + card + "\'")
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateLoansInDB() {
        try {
            con.prepareStatement("UPDATE Loan SET Status=2 WHERE Payed>=TotalPay").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addValueToLoan(long Value, String accountID) {
        try {
            ResultSet r = runCommand(
                    "SELECT Payed from Loan WHERE AccountID=\'" + accountID + "\' AND Status=" + Loan.PAYING);
            r.next();
            long currentvalue = r.getLong(1);
            long finalvalue = currentvalue + Value;

            con.prepareStatement("UPDATE Loan Set Payed=" + finalvalue + " WHERE Status=\'" + Loan.PAYING
                    + "\' AND AccountID=\'" + accountID + "\'").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean CheckCurrentData(String Username, String Password) {
        try {
            ResultSet r = runCommand("SELECT Password from User WHERE Username =\'" + Username + "\'");
            r.next();
            String currentPassword = r.getString(1);
            if (encoder.encode(Password).equals(currentPassword)) {
                return true;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return false;
    }

    public static boolean IsPasswordMatches(JFXPasswordField first, JFXPasswordField Second) {
        if (first.getText().equals(Second.getText())) {
            return true;
        }
        return false;
    }

    public static String GetFullName(String BIC) {
        try {
            ResultSet r = runCommand(
                    "SELECT FirstName,LastName FROM `User` WHERE ID=(SELECT OwnerID FROM Account WHERE BIC='" + BIC
                            + "')");
            r.next();
            String FirstName = r.getString(1);
            String LastName = r.getString(2);
            return FirstName + " " + LastName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean CheckCardOwner(String BIC, String Owner) {
        try {
            ResultSet r = runCommand("SELECT OwnerID from Account WHERE BIC='" + BIC + "'");
            r.next();
            String OwnerID = r.getString(1);
            return OwnerID.equals(Owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean CheckCurrentCVV(String CVV, String BIC) {
        try {
            ResultSet r = runCommand("Select CVV from Account WHERE BIC=\'" + BIC + "\'");
            r.next();
            String cvv = r.getString(1);
            if (cvv.equals(CVV)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean CheckGuarantor(String ID, String Password) {
        try {
            ResultSet r = runCommand("SELECT Password from User WHERE ID=\'" + ID + "\'");
            r.next();
            String pass = r.getString(1);
            if (encoder.encode(Password).equals(pass)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static void AddLoan(String OwnerID, String AccountID, long Value, int Percent, String GuarantorID,
            int Month) {
        try{

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `Loan`(`OwnerID`, `AccountID`, `Status`, `Value`, `Percentage`, `TotalPay`, `Payed`, `DueDate`, `GuarantorID`) VALUES (?,?,?,?,?,?,?,?,?)");

            ps.setString(1, OwnerID);
            ps.setString(2, AccountID);
            ps.setInt(3,0);
            ps.setLong(4, Value);
            ps.setInt(5, Percent);

            long totalPay=Value*(100+Percent)/100;
            ps.setLong(6, totalPay);
            ps.setLong(7,0);

            int month=LocalDate.now().getMonthValue()+Month;
            int year=LocalDate.now().getYear()+month/12;
            month%=12;
            int day=LocalDate.now().getDayOfMonth();
            ps.setDate(8, Date.valueOf(LocalDate.of( year,month,day )));

            ps.setString(9, GuarantorID);

            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // =============================================================================================
    // Insert methods

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

    public static void addAccount(String ownerID, String bic, String iban, String cvv, String cvv2, LocalDate exDate,
            int status, long value, String accountID) throws Exception {

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `Account`(`OwnerID`, `BIC`, `IBAN`, `CVV`, `CVV2`, `ExDate`, `Status`, `Value`, `AccountID`) VALUES (?,?,?,?,?,?,?,?,?)");

        ps.setString(1, ownerID);
        ps.setString(2, bic);
        ps.setString(3, iban);
        ps.setString(4, cvv);
        ps.setString(5, cvv2);
        ps.setDate(6, Date.valueOf(exDate));
        ps.setInt(7, status);
        ps.setLong(8, value);
        ps.setString(9, accountID);

        ps.executeUpdate();
    }

    public static void addLoan(String ownerID, String accountID, int status, long value, int percentage, long totalPay,
            long payed, LocalDate dueDate, String guarantorid) throws Exception {

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `Loan`(`OwnerID`, `AccountID`, `Status`, `Value`, `Percentage`, `TotalPay`, `Payed`, `DueDate`, `GuarantorID`) VALUES (?,?,?,?,?,?,?,?,?)");

        ps.setString(1, ownerID);
        ps.setString(2, accountID);
        ps.setInt(3, status);
        ps.setLong(4, value);
        ps.setInt(5, percentage);
        ps.setLong(6, totalPay);
        ps.setLong(7, payed);
        ps.setDate(8, Date.valueOf(dueDate));
        ps.setString(9, guarantorid);

        ps.executeUpdate();
    }

    public static void addTransaction(String fromID, String toID, long value, LocalDate date, String transactionID)
            throws Exception {

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `Transaction`(`FromAccountID`, `ToAccountID`, `Value`, `Date`, `TransactionID`) VALUES (?,?,?,?,?)");

        ps.setString(1, fromID);
        ps.setString(2, toID);
        ps.setLong(3, value);
        ps.setDate(4, Date.valueOf(date));
        ps.setString(5, transactionID);

        ps.executeUpdate();
    }

    public static boolean containsBIC(String bic) {
        ResultSet r;
        try {
            r = runCommand("Select * From Account Where BIC=\'" + bic + "\'");

            if (r.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public static int numberOfAccounts() {
        int count = 0;
        try {
            ResultSet r = runCommand("Select * from Account");

            while (r.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /*
     * public static void addLoan(String OwnerID, String accountID, int status, long
     * value, int percentage, long totalPay, long payed, Date dueDate, String
     * guarantorid) throws Exception { runCommand(
     * "INSERT INTO Loan (FirstName LastName Username Password AccessLevel Address ID NationalCode BirthDate) Values (\'"
     * + firstname + "\'" + "\'" + lastname + "\'" + "\'" + Username + "\'" + "\'" +
     * Password + "\'" + AccessLevel + "\'" + address + "\'" + id + "\'" +
     * nationalCode + "\'" + birthDate); }
     */

    // =============================================================================================

    public static ResultSet getLoans(String OwnerID) throws Exception {
        return runCommand("select * from Loan where OwnerID=\'" + OwnerID + "\'");
    }

    public static ResultSet getAccounts(String OwnerID) throws Exception {
        return runCommand("select * from Account where OwnerID=\'" + OwnerID + "\'");
    }

    public static ResultSet getTransactions(String OwnerID) throws Exception {
        return runCommand(
                "SELECT * FROM Transaction WHERE FromAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID='"
                        + OwnerID + "') OR  ToAccountID= ANY(SELECT AccountID FROM Account WHERE OwnerID='" + OwnerID
                        + "')");
    }

    public static User getUser(String username) {
        try {
            ResultSet r = runCommand("select * from User where Username=\'" + username + "\'");

            if (r.next()) {
                return (new User(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getInt(5),
                        r.getString(6), r.getString(7), r.getString(8), r.getDate(9).toLocalDate(), r.getString(10),
                        r.getString(11), r.getInt(12), r.getInt(13)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int numberOfTransactions() {
        int count = 0;
        try {
            ResultSet r = runCommand("Select * from Transaction");

            while (r.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public static ArrayList<User> getAllUsers(int AccessLevel) {
        ArrayList<User> allUsers = new ArrayList<>();
        try {
            ResultSet r = runCommand("SELECT * FROM User WHERE AccessLevel=" + AccessLevel);
            while (r.next()) {
                User user = new User(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getInt(5),
                        r.getString(6), r.getString(7), r.getString(8), r.getDate(9).toLocalDate(), r.getString(10),
                        r.getString(11), r.getInt(12), r.getInt(13));
                allUsers.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public static ResultSet getAllAccounts() throws Exception {
        return runCommand("Select * from Account");
    }

    public static ResultSet getAllLoans() throws Exception {
        return runCommand("Select * from Loan");
    }

    public static ResultSet getAllTransactions() throws Exception {
        return runCommand("Select * from Transaction");
    }

    public static void updateLoan(String accountID, int status) {
        try {
            con.prepareStatement(
                    "UPDATE `Loan` SET Status=" + status + " WHERE Status=0 AND AccountID=\'" + accountID + "\'")
                    .executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}