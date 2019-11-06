package fr.unice.polytech.creditrama.clients;

import fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling;
import fr.unice.polytech.creditrama.clients.enums.MaritalStatus;
import fr.unice.polytech.creditrama.clients.enums.WorkField;
import fr.unice.polytech.creditrama.transactions.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.unice.polytech.creditrama.Utils.*;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.*;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.*;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.RETIRED;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;
import static java.time.LocalDateTime.now;

public class AdultCompulsive extends Client {

    public AdultCompulsive() {
        super();
        dateOfBirth(25, 35);
        maritalStatus();
        childrenCount();
        securityNumber();
        weight(getAge());
        height(getAge());
        levelOfSchooling();
        workField();
        monthlyIncome();
    }

    private void maritalStatus() {
        setMaritalStatus(pickFrom(new MaritalStatus[]{SINGLE, MARRIED, ENGAGED, COHABITING}));
    }

    private void childrenCount() {
        boolean chances;
        if (Arrays.asList(MARRIED, ENGAGED, COHABITING).contains(getMaritalStatus())) {
            int random = randIntBetween(0, 100);
            chances = getAge() < 30 ? random < 25 : random < 75;
        } else {
            chances = randIntBetween(0, 100) < 5;
        }

        setChildrenCount(chances ? 1 : 0);
    }

    private void levelOfSchooling() {
        setLevelOfSchooling(pickFrom(new LevelOfSchooling[]{UNDERGRADUATE, MASTER, PhD, GRADUATE}));
    }

    private void workField() {
        setWorkField(pickFromExcept(WorkField.values(), new WorkField[]{STUDENT, RETIRED}));
    }

    private void monthlyIncome() {
        setMonthlyIncome(randIntBetween(1900, 3500));
    }

    @Override
    public List<Transaction> makeTransactions(int number, LocalDateTime accountCreation) {
        long accountID = getAccountID();
        long clientID = getClientID();

        List<Transaction> transactions = new ArrayList<>();

        LocalDateTime time = accountCreation;

        // He spends all of what he earns... Sometimes more, sometimes he wins 100 in lottery
        while (time.isBefore(now())) {
            time = time.plusMonths(1);
            transactions.add(new Transaction(accountID, clientID, getMonthlyIncome(), time));

            int split = randIntBetween(1, 5);

            for (int i = 0; i < split; i++) {
                int amount = (int) (getMonthlyIncome() / split);
                int day = randIntBetween(1, 28);
                transactions.add(new Transaction(accountID, clientID, -amount, time.withDayOfMonth(day)));
            }

            boolean coin = randIntBetween(0, 100) < 50;

            transactions.add(new Transaction(accountID, clientID, coin ? -100 : 100, time));
        }

        return transactions;
    }
}
