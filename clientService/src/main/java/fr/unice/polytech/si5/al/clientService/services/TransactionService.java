package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final TimeService timeService;

    public TransactionService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, TimeService timeService) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.timeService = timeService;
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
                Transaction saved = transactionRepository.save(transaction);

                System.out.println(saved.localDateTime().toString());
                System.out.println("----------------");

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

        System.out.println("----------------");
        System.out.println(current.localDateTime().toString());

        if (!optPrevious.isPresent()) {
            return;
        }

        Transaction previous = optPrevious.get();

        LocalDateTime prevTime = previous.localDateTime();
        LocalDateTime currentTime = current.localDateTime();

        long timeSpan = ChronoUnit.MILLIS.between(prevTime, currentTime);

        if (timeSpan < 1000) {
            System.out.println(timeSpan + " between " + prevTime.toString() + " and " + currentTime.toString());
            throw new TransactionException("new transaction too fast on same account");
        }
    }

    private void checkTimeSynchro(Transaction transaction) throws TransactionException {
        long timeSpan = ChronoUnit.SECONDS.between(transaction.localDateTime(), timeService.getCurrentTime());

        if (Math.abs(timeSpan) > 3) {
            System.out.println(timeSpan + " too big");
            throw new TransactionException("this transaction has a timestamp too different compared to our system !");
        }
    }
}
