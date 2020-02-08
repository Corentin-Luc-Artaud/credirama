package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.FailRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MILLIS;

@Service
public class TransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final TimeService timeService;
    private final FailRepository failRepository;

    public TransactionService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, TimeService timeService, FailRepository failRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.timeService = timeService;
        this.failRepository = failRepository;
    }

    public void handleTransaction(Transaction transaction) throws TransactionException {

        Optional<BankAccount> clientAccount = bankAccountRepository.findById(transaction.getAccountID());

        if (clientAccount.isPresent()) {
            BankAccount account = clientAccount.get();

            if (account.getClientId() == transaction.getClientID()) {

                checkTimeSynchro(transaction);
                checkPreviousTransaction(account, transaction);

                account.setAmount(account.getAmount() + transaction.getAmount());

                bankAccountRepository.save(account);
                transactionRepository.save(transaction);
            } else {
                throw new TransactionException("no account has been found given " +
                        "the accountId and the clientId "
                        + " transaction id client = " + transaction.getClientID()
                        + " account id client = " + clientAccount.get().getClientId()
                );
            }
        } else {
            throw new TransactionException("no account has been found given" +
                    " the accountId");
        }
    }

    private void checkPreviousTransaction(BankAccount account, Transaction current) throws TransactionException {
        Optional<Transaction> optPrevious = transactionRepository.findFirstByAccountIDOrderByTransactionTimeDesc(account.getId());

        if (!optPrevious.isPresent()) {
            return;
        }

        Transaction previous = optPrevious.get();

        LocalDateTime prevTime = previous.localDateTime();
        LocalDateTime currentTime = current.localDateTime();

        long timeSpan = MILLIS.between(prevTime, currentTime);

        if (timeSpan < 1000) {
            throw new TransactionException("new transaction too fast on same account");
        }
    }

    private void checkTimeSynchro(Transaction transaction) throws TransactionException {
        long timeSpan = MILLIS.between(transaction.localDateTime(), timeService.getCurrentTime());

        if (Math.abs(timeSpan) > 3000) {
            failRepository.save(transaction);
            introspection();
            throw new TransactionException("this transaction has a timestamp too different compared to our system !");
        }
    }

    private void introspection() {
        long count = failRepository.count();

        if (count > 3) {
            failRepository.deleteAll();
            timeService.recoverAtomicTime();
        }
    }
}
