package model;

import java.util.*;

public class User {
    String FirstName, LastName, Username, Password, Address, ID, NationalCode;
    Date BirthDate;
    int AccessLevel, Theme, Language;

    ArrayList<Loan> Loans = new ArrayList<Loan>();
    ArrayList<Account> Accounts = new ArrayList<Account>();

    public User(String firstName, String lastName, String password, String address, String id, String nationalCode,
            Date birthDate, int accessLevel) {
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Address = address;
        ID = id;
        NationalCode = nationalCode;
        BirthDate = birthDate;
        AccessLevel = accessLevel;
    }

    public void addToLoans(Loan l) {
        Loans.add(l);
    }

    public ArrayList<Loan> getLoans() {
        return Loans;
    }

    public void addToAccounts(Account a) {
        Accounts.add(a);
    }

    public ArrayList<Account> getAccounts() {
        return Accounts;
    }

    public static String generateID(int AccessLevel, Long SameAccessUsersCount) {
        // (C/E/M)-(00001-99999)
        String[] alph = { "C", "E", "M" };
        String ID = alph[AccessLevel] + "-";
        String number = Long.toString(SameAccessUsersCount + 1);
        for (int i = 0; i < (5 - number.length()); i++) {
            ID += "0";
        }
        ID += number;

        return ID;
    }
}
