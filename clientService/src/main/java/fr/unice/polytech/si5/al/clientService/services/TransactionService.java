package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void handleTransaction(Transaction transaction) throws TransactionException {
        System.out.println("bank account length : " + bankAccountRepository.count());
        Optional<BankAccount> clientAccount = bankAccountRepository.findById(transaction.getIdAccount());

        if (clientAccount.isPresent()){
            if(clientAccount.get().getClientId() == transaction.getIdClient()) {
                int currentAmount = clientAccount.get().getAmount();
                clientAccount.get().setAmount(currentAmount + transaction.getDifference());
                bankAccountRepository.save(clientAccount.get());
                transactionRepository.save(transaction);
            }else {
                throw new TransactionException("no account has been found given " +
                        "the accountId and the clientId "
                        + " transaction id client = " + transaction.getIdClient()
                        + " account id client = " + clientAccount.get().getClientId()
                );
            }
        }else {
            throw new TransactionException("no account has been found given" +
                    " the accountId");
        }
    }
}
