package fr.unice.polytech.si5.al.clientService.repositories;

import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    @Override
    Iterable<BankAccount> findAll();

    Optional<BankAccount> findById(long id);
}
