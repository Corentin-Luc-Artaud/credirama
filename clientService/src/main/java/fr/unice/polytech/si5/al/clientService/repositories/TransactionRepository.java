package fr.unice.polytech.si5.al.clientService.repositories;

import fr.unice.polytech.si5.al.clientService.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Override
    Iterable<Transaction> findAll();

    Optional<Transaction> findFirstByAccountIDOrderByTimestampDesc(long id);

    Iterable<Transaction> findAllByAccountID(long id);
}
