package model;

import java.sql.*;
import java.util.*;

public class UserController {
    private static User CurrentUser;
    private static ArrayList<Account> Accounts = new ArrayList<Account>();
    private static ArrayList<Loan> Loans = new ArrayList<Loan>();
    private static ArrayList<Transaction> Transactions = new ArrayList<Transaction>();

    public static User getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(User u) {
        CurrentUser = u;

        LoadUserDataFromDB();
    }

    public static void LoadUserDataFromDB() {
        if (CurrentUser.AccessLevel == User.CLIENT) {
            try {
                Accounts = ConvertAccountsToArrayList(DBConnector.getAccounts(CurrentUser.ID));
                Loans = ConvertLoansToArrayList(DBConnector.getLoans(CurrentUser.ID));

                for (int i = 0; i < Accounts.size(); i++) {
                    ArrayList<Transaction> temp = ConvertTransactionsToArrayList(
                            DBConnector.getTransactions(Accounts.get(i).AccountID));

                    for (Transaction t : temp) {
                        Transactions.add(t);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Accounts = ConvertAccountsToArrayList(DBConnector.getAllAccounts());
                Loans = ConvertLoansToArrayList(DBConnector.getAllLoans());
                Transactions = ConvertTransactionsToArrayList(DBConnector.getAllTransactions());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //System.out.println(Arrays.deepToString(Accounts.toArray()));
        System.out.println(Arrays.deepToString(Loans.toArray()));
        getCurrentUser().setLoans(Loans);
        getCurrentUser().setAccounts(Accounts);
        System.out.println(Loans.size());
        //System.out.println(Arrays.deepToString(Transactions.toArray()));
    }

    private static ArrayList<Account> ConvertAccountsToArrayList(ResultSet all) {
        ArrayList<Account> temp = new ArrayList<Account>();

        try {
            while (all.next()) {
                Account a = new Account(all.getString(1), all.getString(2), all.getString(3), all.getString(4),
                        all.getString(5), all.getDate(6).toLocalDate(), all.getInt(7), all.getLong(8),
                        all.getString(9));

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
                Loan l = new Loan(CurrentUser.ID, all.getString("AccountID"), all.getInt("Status"),
                        all.getLong("Value"), all.getInt("Percentage"), all.getLong("TotalPay"), all.getLong("Payed"),
                        all.getDate("DueDate").toLocalDate(), all.getString("GuarantorID"));

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
                        all.getLong("Value"), all.getDate("Date").toLocalDate(), all.getString("TransactionID"));

                temp.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static ArrayList<Loan> getLoans() {
        return Loans;
    }
}
