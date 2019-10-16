package fr.unice.polytech.si5.al.clientService.components;

import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreator {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void createNewAccount(Long idClient){
        BankAccount newBankAccount = BankAccount.builder()
                .clientId(idClient)
                .amount(0)
                .build();
        bankAccountRepository.save(newBankAccount);
    }
}
