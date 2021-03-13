package model;

import java.util.Date;

public class Loan {
    User Client, Guarantor;
    int Status, Percntage;
    long Value, TotalPay, Payed;
    Date DueDate;

    public Loan(User client, int status, long value, int percentage, long totalPay, long payed, Date dueDate,
            User guarantor) {
        Client = client;
        Guarantor = guarantor;
        Status = status;
        Percntage = percentage;
        Value = value;
        TotalPay = totalPay;
        Payed = payed;
        DueDate = dueDate;
    }
}
