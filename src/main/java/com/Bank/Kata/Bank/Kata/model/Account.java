package com.Bank.Kata.Bank.Kata.model;

import com.Bank.Kata.Bank.Kata.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class Account {
    private final TransactionRepository transactionRepository;

    @Autowired
    public Account(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void deposit(int amount, LocalDate date) {
        transactionRepository.save(new Transaction(amount, "DEPOSIT", date));
    }

    public void withdraw(int amount, LocalDate date) {
        transactionRepository.save(new Transaction(-amount, "WITHDRAWAL", date));
    }

    public void printStatement() {
        System.out.println("DATE | AMOUNT | BALANCE");

        List<Transaction> transactions = transactionRepository.findAll();

        // 🔹 Trier d'abord les transactions du plus ancien au plus récent pour calculer le solde correctement
        transactions.sort(Comparator.comparing(Transaction::getDate));

        int balance = 0;
        List<String> statementLines = new ArrayList<>();

        // 🔹 Calcul du solde transaction par transaction
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
            statementLines.add(transaction.getDate() + " | " + transaction.getAmount() + " | " + balance);
        }

        // 🔹 Afficher dans l'ordre inverse (du plus récent au plus ancien)
        Collections.reverse(statementLines);
        for (String line : statementLines) {
            System.out.println(line);
        }
    }

}
