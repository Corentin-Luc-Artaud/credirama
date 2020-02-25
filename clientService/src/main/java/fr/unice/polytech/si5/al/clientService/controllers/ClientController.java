package fr.unice.polytech.si5.al.clientService.controllers;

import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/")
    public ResponseEntity newClientRegisters(@Valid @RequestBody Client client) {

        System.out.println("Accessed POST");
        String s = clientService.addNewClient(client);
        System.out.println(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity getClients() {
        System.out.println("Access GET");

        return new ResponseEntity<>(clientService.getAllClients().toString(), HttpStatus.OK);
    }
}
