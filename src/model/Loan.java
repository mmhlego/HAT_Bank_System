package model;

import java.time.LocalDate;

public class Loan {
    public static int PENDING = 0, REJECTED = 1, PAYING = 2, FINISHED = 3;

    String OwnerID, AccountID, GuarantorID;
    int Status, Percntage;
    long Value, TotalPay, Payed;
    LocalDate DueDate;

    public Loan(String ownerID, String accountID, int status, long value, int percentage, long totalPay, long payed,
            LocalDate dueDate, String guarantorid) {
        OwnerID = ownerID;
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
