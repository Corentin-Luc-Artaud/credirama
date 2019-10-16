package fr.unice.polytech.si5.al.clientService.controllers;

import fr.unice.polytech.si5.al.clientService.models.Client;
import fr.unice.polytech.si5.al.clientService.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/", method = RequestMethod.POST)

    public ResponseEntity newClientRegisters(@Valid @RequestBody Client client) {
        return new ResponseEntity(clientService.addNewClient(client), HttpStatus.OK);
    }
}
