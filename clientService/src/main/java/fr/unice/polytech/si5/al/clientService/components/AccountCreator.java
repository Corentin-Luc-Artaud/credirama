package fr.unice.polytech.si5.al.clientService.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;

@Component
public class AccountCreator {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final Logger logger = LogManager.getLogger("AccountCreator");

    public void createNewAccount(Long idClient){
        BankAccount newBankAccount = BankAccount.builder()
                .clientId(idClient)
                .amount(0)
                .build();
        bankAccountRepository.save(newBankAccount);
        logger.info("New bank account has been created for client " + idClient);
    }
}
