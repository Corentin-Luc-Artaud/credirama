package fr.unice.polytech.si5.al.clientService.repositories;

import fr.unice.polytech.si5.al.clientService.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    Iterable<Client> findAll();

    Optional<Client> findById(long id);
}
