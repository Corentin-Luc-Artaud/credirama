package fr.unice.polytech.si5.al.clientService.components;

import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreator {

    private static final Logger logger = LogManager.getLogger("AccountCreator");

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public long createNewAccount(Long idClient) {
        BankAccount newBankAccount = BankAccount.builder()
                .clientId(idClient)
                .amount(0)
                .build();
        bankAccountRepository.save(newBankAccount);
        logger.info("New bank account has been created for client " + idClient);
        return newBankAccount.getClientId();
    }
}
