package model;

import java.time.LocalDate;
import java.util.*;

public class User {
    public static final int CLIENT = 0, EMPLOYEE = 1, MANAGER = 2;

    public String FirstName, LastName, Username, Password, Email, PhoneNumber, Address, ID, NationalCode;
    public LocalDate BirthDate;
    public int AccessLevel, Theme, Language;

    public ArrayList<Loan> Loans = new ArrayList<Loan>();
    public ArrayList<Account> Accounts = new ArrayList<Account>();

    public User(String firstName, String lastName, String username, String password, String email, String phone,
            int accessLevel, String address, String id, String nationalCode, LocalDate birthDate, int theme, int language) {
        FirstName = firstName;
        LastName = lastName;
        Username = username;
        Password = password;
        Email = email;
        PhoneNumber = phone;
        AccessLevel = accessLevel;

        Address = address;
        ID = id;
        NationalCode = nationalCode;
        BirthDate = birthDate;
        Theme = theme;
        Language = language;
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
