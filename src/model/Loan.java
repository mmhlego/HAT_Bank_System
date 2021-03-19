package model;

import java.util.Date;

public class Loan {
    User Client;
    String AccountID, GuarantorID;
    int Status, Percntage;
    long Value, TotalPay, Payed;
    Date DueDate;

    public Loan(User client, String accountID, int status, long value, int percentage, long totalPay, long payed,
            Date dueDate, String guarantorid) {
        Client = client;
        AccountID = accountID;
        GuarantorID = guarantorid;
        Status = status;
        Percntage = percentage;
        Value = value;
        TotalPay = totalPay;
        Payed = payed;
        DueDate = dueDate;
    }
}
