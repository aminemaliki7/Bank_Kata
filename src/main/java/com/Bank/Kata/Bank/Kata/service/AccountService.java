package com.Bank.Kata.Bank.Kata.service;

import java.time.LocalDate;

public interface AccountService {
    void deposit(int amount, LocalDate date);
    void withdraw(int amount, LocalDate date);
    void printStatement();
}
