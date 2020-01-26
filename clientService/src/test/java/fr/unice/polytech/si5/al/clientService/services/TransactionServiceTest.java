package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @MockBean
    private TransactionService service;
    @MockBean
    private BankAccountRepository bankAccountRepo;
    @MockBean
    private TransactionRepository transactionRepo;

    private TimeService timeService;

    private Transaction first;
    private Transaction second;
    private Transaction third;
    private Transaction fourth;
    private Transaction fifth;
    private Transaction sixth;

    private BankAccount bankAccount = new BankAccount();

    List<Transaction> transactions = new ArrayList<>();

    @Before
    public void setUp() {
        bankAccountRepo = Mockito.mock(BankAccountRepository.class);
        transactionRepo = Mockito.mock(TransactionRepository.class);
        timeService = Mockito.mock(TimeService.class);

        setupObjectsInstances();
        setupMocks();

        service = new TransactionService(bankAccountRepo, transactionRepo, timeService);
    }

    private void setupObjectsInstances() {
        long clientId = 5;

        bankAccount.setClientId(clientId);
        bankAccount.setAmount(300);

        LocalDateTime start = LocalDateTime.now();
        long accountId = bankAccount.getId();

        first = new Transaction(accountId, clientId, 200, start);
        second = new Transaction(accountId, clientId, 200, start.plus(1200, ChronoUnit.MILLIS));
        third = new Transaction(accountId, clientId, 200, start.plus(1300, ChronoUnit.MILLIS));
        fourth = new Transaction(accountId, clientId, 200, start.plusMinutes(1));
        fifth = new Transaction(accountId, clientId, 200, start.minusMinutes(1));
        sixth = new Transaction(accountId, clientId, 200, start);
    }

    private void setupMocks() {
        when(bankAccountRepo.findById(anyLong())).thenReturn(Optional.of(bankAccount));

        Arrays.asList(first, second, third, fourth, fifth).forEach(transaction -> {
            when(transactionRepo.save(transaction)).then(__ -> {
                transactions.add(transaction);
                return transaction;
            });
        });

        when(transactionRepo.findFirstByAccountIDOrderByTransactionTimeDesc(anyLong()))
                .then(__ -> {
                    if (transactions.isEmpty()) {
                        return Optional.empty();
                    } else {
                        int index = transactions.size() - 1;
                        return Optional.of(transactions.get(index));
                    }
                });

        when(timeService.getCurrentTime()).thenReturn(LocalDateTime.now());
    }

    private void mockTimeServiceToFail() {
        when(timeService.getCurrentTime()).thenReturn(LocalDateTime.now().minusMinutes(4));
    }

    @Test
    public void handleTransactions() throws TransactionException {
        // This is the first transaction.
        // Everything should be OK
        service.handleTransaction(first);

        // This transaction is 1.2 seconds after the first one
        // Everything should be OK
        service.handleTransaction(second);

        try {
            // This transaction is 100ms after the second one !
            // It should fail because too fast after previous one
            service.handleTransaction(third);
            fail();
        } catch (TransactionException te) {
            assertEquals("new transaction too fast on same account", te.getMessage());
        }
    }

    @Test
    public void transactionTooEarly() {
        try {
            // This transaction has a bad timestamp : different by -1 min
            // It should fail because too de-synchronized with our service
            service.handleTransaction(fourth);
            fail();
        } catch (TransactionException te) {
            assertEquals("this transaction has a timestamp too different compared to our system !", te.getMessage());
        }
    }

    @Test
    public void transactionTooLate() {
        try {
            // This transaction has a bad timestamp : different by +1 min
            // It should fail because too de-synchronized with our service
            service.handleTransaction(fifth);
            fail();
        } catch (TransactionException te) {
            assertEquals("this transaction has a timestamp too different compared to our system !", te.getMessage());
        }
    }

    @Test
    public void transactionWithTimeServiceFail() {
        mockTimeServiceToFail();

        try {
            // It should fail because too de-synchronized with our service
            service.handleTransaction(sixth);
            fail();
        } catch (TransactionException te) {
            assertEquals("this transaction has a timestamp too different compared to our system !", te.getMessage());
        }
    }
}