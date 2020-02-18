package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.FailRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @MockBean
    private TransactionService service;
    @MockBean
    private BankAccountRepository bankAccountRepo;
    @MockBean
    private TransactionRepository transactionRepo;
    @MockBean
    private FailRepository failRepository;

    private TimeService timeService;

    private Transaction first;
    private Transaction second;
    private Transaction third;
    private Transaction fourth;
    private Transaction fifth;
    private Transaction sixth;

    private BankAccount bankAccount = new BankAccount();

    List<Transaction> transactions = new ArrayList<>();
    List<Transaction> failedTransactions = new ArrayList<>();

    @Before
    public void setUp() {
        bankAccountRepo = mock(BankAccountRepository.class);
        transactionRepo = mock(TransactionRepository.class);
        failRepository = mock(FailRepository.class);
        timeService = mock(TimeService.class);

        setupObjectsInstances();
        setupMocks();

        service = new TransactionService(bankAccountRepo, transactionRepo, timeService, failRepository);
    }

    private void setupObjectsInstances() {
        long clientId = 5;

        bankAccount.setClientId(clientId);
        bankAccount.setAmount(300);

        LocalDateTime start = LocalDateTime.now(UTC);
        long accountId = bankAccount.getId();

        first = new Transaction(accountId, clientId, 200, toLong(start), "+01:00");
        second = new Transaction(accountId, clientId, 200, toLong(start.plus(1200, MILLIS)), "+01:00");
        third = new Transaction(accountId, clientId, 200, toLong(start.plus(1300, MILLIS)), "+01:00");
        fourth = new Transaction(accountId, clientId, 200, toLong(start.plusMinutes(2)), "+01:00");
        fifth = new Transaction(accountId, clientId, 200, toLong(start.minusMinutes(2)), "+01:00");
        sixth = new Transaction(accountId, clientId, 200, toLong(start), "+01:00");
    }

    long toLong(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.ofHours(0)).toEpochMilli();
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

        when(timeService.getCurrentTime()).thenReturn(LocalDateTime.now(UTC).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());

        when(failRepository.saveAll(anyList())).then(methodCall -> {
            failedTransactions.addAll(methodCall.getArgument(0));
            return failedTransactions;
        });

        when(failRepository.save(any())).then(methodCall -> {
            Transaction transaction = methodCall.getArgument(0);
            failedTransactions.add(transaction);
            return transaction;
        });

        when(failRepository.count()).then(__ -> (long) failedTransactions.size());

        Mockito.doAnswer(__ -> {
            failedTransactions.clear();
            return null;
        }).when(failRepository).deleteAll();

        Mockito.doAnswer(__ -> {
            recoverTimeService();
            return null;
        }).when(timeService).recoverAtomicTime();
    }

    private void mockTimeServiceToFail() {
        when(timeService.getCurrentTime()).thenReturn(LocalDateTime.now(UTC).minusMinutes(4).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
    }

    private void recoverTimeService() {
        when(timeService.getCurrentTime()).thenReturn(LocalDateTime.now(UTC).toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
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

    @Test
    public void triggeringRecovery() throws TransactionException {
        failRepository.saveAll(Arrays.asList(first, second, third));//, fourth, fifth));

        assertEquals(3, failRepository.count());

        mockTimeServiceToFail();

        // It should fail because too de-synchronized with our service
        // This will trigger the time-service recovery
        try {
            service.handleTransaction(sixth);
        } catch (TransactionException ignored) {
        }

        assertEquals(0, failRepository.count());

        service.handleTransaction(sixth);

        assertEquals(timeService.getCurrentTime(), LocalDateTime.now(UTC).toInstant(ZoneOffset.ofHours(0)).toEpochMilli(), 10);
        assertEquals(0, failRepository.count());
    }


}
