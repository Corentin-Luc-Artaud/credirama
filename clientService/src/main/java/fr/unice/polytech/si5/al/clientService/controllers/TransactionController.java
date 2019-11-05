package fr.unice.polytech.si5.al.clientService.controllers;

import fr.unice.polytech.si5.al.clientService.exceptions.TransactionException;
import fr.unice.polytech.si5.al.clientService.models.Transaction;
import fr.unice.polytech.si5.al.clientService.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/")
    public ResponseEntity fillAccount(@Valid @RequestBody Transaction transaction){

        try {
            transactionService.handleTransaction(transaction);
        } catch (TransactionException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Transaction done", HttpStatus.OK);
    }
}
