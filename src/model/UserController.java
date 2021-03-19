package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private static User CurrentUser;
    private static ArrayList<Account> Accounts = new ArrayList<Account>();
    private static ArrayList<Loan> Loans = new ArrayList<Loan>();
    private static ArrayList<Transaction> Transactions = new ArrayList<Transaction>();

    public static User getCurrentUsr() {
        return CurrentUser;
    }

    public static void setCurrentUser(User u) {
        CurrentUser = u;

        LoadUserDataFromDB();
    }

    public static void LoadUserDataFromDB() {
        try {
            Accounts = ConvertAccountsToArrayList(DBConnector.getAccounts(CurrentUser.ID));
            //Loans = ConvertLoansToArrayList(DBConnector.getLoans(CurrentUser.ID));
            //Transactions = ConvertTransactionsToArrayList(DBConnector.getTransactions(CurrentUser.ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Account> ConvertAccountsToArrayList(ResultSet all) {
        ArrayList<Account> temp = new ArrayList<Account>();

        try {
            while (all.next()) {
                Account a = new Account(all.getString(1), all.getString(2), all.getString(3), all.getString(4),
                        all.getDate(5), all.getInt(6), all.getLong(7), all.getString(8));

                temp.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private static ArrayList<Loan> ConvertLoansToArrayList(ResultSet all) {
        ArrayList<Loan> temp = new ArrayList<Loan>();

        try {
            while (all.next()) {
                Loan l = new Loan(CurrentUser, all.getString("AccountID"), all.getInt("Status"), all.getLong("Value"),
                        all.getInt("Percentage"), all.getLong("TotalPay"), all.getLong("Payed"), all.getDate("DueDate"),
                        all.getString("GuarantorID"));

                temp.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    private static ArrayList<Transaction> ConvertTransactionsToArrayList(ResultSet all) {
        ArrayList<Transaction> temp = new ArrayList<Transaction>();

        try {
            while (all.next()) {
                Transaction t = new Transaction(all.getString("FromAccountID"), all.getString("ToAccountID"),
                        all.getLong("Value"), all.getDate("Date"), all.getString("TransactionID"));

                temp.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
