package fr.unice.polytech.si5.al.clientService.controllers;

import com.google.gson.Gson;
import fr.unice.polytech.si5.al.clientService.models.BankAccount;
import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.repositories.BankAccountRepository;
import fr.unice.polytech.si5.al.clientService.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dump")
public class DumpController {

    private final ClientRepository clientRepository;

    private final BankAccountRepository accountRepository;

    public DumpController(ClientRepository clientRepository, BankAccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/")
    public ResponseEntity getClients() {
        List<ClientAccountLinked> res = new ArrayList<>();
        List<BankAccount> accounts = new ArrayList<>();
        for (BankAccount account : accountRepository.findAll()) {
            accounts.add(account);
        }
        for (Client client : clientRepository.findAll()){
            List<BankAccount> clientsAccount = accounts.stream().filter(account -> account.getClientId() == client.getId()).collect(Collectors.toList());
            ClientAccountLinked link = new ClientAccountLinked(client, clientsAccount);
            res.add(link);
            accounts.removeAll(clientsAccount);
        }
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    @AllArgsConstructor
    @Data
    private static class ClientAccountLinked {
        private Client client;
        private List<BankAccount> accounts;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }
}