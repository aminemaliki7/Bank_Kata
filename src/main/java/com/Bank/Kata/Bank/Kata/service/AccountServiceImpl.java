package com.Bank.Kata.Bank.Kata.service;

import com.Bank.Kata.Bank.Kata.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountServiceImpl implements AccountService {
    private final Account account;

    @Autowired
    public AccountServiceImpl(Account account) {
        this.account = account;
    }

    @Override
    public void deposit(int amount, LocalDate date) {
        account.deposit(amount, date);
    }

    @Override
    public void withdraw(int amount, LocalDate date) {
        account.withdraw(amount, date);
    }

    @Override
    public void printStatement() {
        account.printStatement();
    }
}
