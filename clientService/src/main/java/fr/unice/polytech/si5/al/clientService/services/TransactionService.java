package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void handleTransaction(Transaction transaction) throws TransactionException {

        Optional<BankAccount> clientAccount = bankAccountRepository.findById(transaction.getAccountID());

        if (clientAccount.isPresent()){
            if(clientAccount.get().getClientId() == transaction.getClientID()) {
                int currentAmount = clientAccount.get().getAmount();
                clientAccount.get().setAmount(currentAmount + transaction.getAmount());
                bankAccountRepository.save(clientAccount.get());
                transactionRepository.save(transaction);
            }else {
                throw new TransactionException("no account has been found given " +
                        "the accountId and the clientId "
                        + " transaction id client = " + transaction.getClientID()
                        + " account id client = " + clientAccount.get().getClientId()
                );
            }
        }else {
            throw new TransactionException("no account has been found given" +
                    " the accountId");
        }
    }
}
