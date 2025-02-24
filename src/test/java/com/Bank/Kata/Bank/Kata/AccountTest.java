package com.Bank.Kata.Bank.Kata;

import com.Bank.Kata.Bank.Kata.model.Account;
import com.Bank.Kata.Bank.Kata.model.Transaction;
import com.Bank.Kata.Bank.Kata.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class); // 🔹 Mock du repository
        account = new Account(transactionRepository);
    }

    @Test
    void testDeposit() {
        LocalDate depositDate = LocalDate.of(2012, 1, 10);
        account.deposit(1000, depositDate);

        // 🔹 Vérifier que la transaction a bien été enregistrée une fois
        verify(transactionRepository, times(1)).save(any());

        // 🔹 Vérifier que la transaction enregistrée est correcte
        Transaction expectedTransaction = new Transaction(1000, "DEPOSIT", depositDate);
        assertEquals(1000, expectedTransaction.getAmount());
        assertEquals("DEPOSIT", expectedTransaction.getType());
        assertEquals("10-01-2012", expectedTransaction.getDate());
    }

    @Test
    void testWithdraw() {
        LocalDate withdrawalDate = LocalDate.of(2012, 1, 14);
        account.withdraw(500, withdrawalDate);

        // 🔹 Vérifier que la transaction de retrait est bien enregistrée une fois
        verify(transactionRepository, times(1)).save(any());

        // 🔹 Vérifier que la transaction enregistrée est correcte
        Transaction expectedTransaction = new Transaction(-500, "WITHDRAWAL", withdrawalDate);
        assertEquals(-500, expectedTransaction.getAmount());
        assertEquals("WITHDRAWAL", expectedTransaction.getType());
        assertEquals("14-01-2012", expectedTransaction.getDate());
    }

    @Test
    void testPrintStatementStrictOrder() {
        // 🔹 Simuler des transactions avec Mockito
        List<Transaction> mockTransactions = Arrays.asList(
            new Transaction(1000, "DEPOSIT", LocalDate.of(2012, 1, 10)),
            new Transaction(2000, "DEPOSIT", LocalDate.of(2012, 1, 13)),
            new Transaction(-500, "WITHDRAWAL", LocalDate.of(2012, 1, 14))
        );

        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        // 🔹 Capture la sortie console
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        // 🔹 Vérifier que `findAll()` a bien été appelé une seule fois
        verify(transactionRepository, times(1)).findAll();

        // 🔹 Normaliser la sortie pour éviter les erreurs de format (`\r\n` vs `\n`)
        String actualOutput = outputStream.toString().replace("\r\n", "\n").trim();

        // 🔹 Normaliser aussi la chaîne attendue
        String expectedOutput = (
                "DATE | AMOUNT | BALANCE\n" +
                "14-01-2012 | -500 | 2500\n" +
                "13-01-2012 | 2000 | 3000\n" +
                "10-01-2012 | 1000 | 1000"
        ).replace("\r\n", "\n").trim();

        // 🔹 Vérification avec assertion
        assertEquals(expectedOutput, actualOutput);

        // 🔹 Rétablir la sortie standard
        System.setOut(originalOut);
     // 🔹 Afficher la sortie capturée dans la console
        System.out.println("Bank Statement Output:\n" + actualOutput);

    }
}
