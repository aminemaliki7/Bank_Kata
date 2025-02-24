package com.Bank.Kata.Bank.Kata.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final int amount;
    private final LocalDate date;
    private final String type;

    public Transaction(int amount, String type, LocalDate date) {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getType() {
        return type;
    }
}
