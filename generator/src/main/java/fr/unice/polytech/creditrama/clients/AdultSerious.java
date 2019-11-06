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

public class AdultSerious extends Client {

    public AdultSerious() {
        super();
        dateOfBirth(30, 55);
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

        if (getChildrenCount() == 1) {
            boolean secondChild = randIntBetween(0, 100) < 20;
            setChildrenCount(secondChild ? 2 : 1);
        }
    }

    private void levelOfSchooling() {
        setLevelOfSchooling(pickFrom(new LevelOfSchooling[]{UNDERGRADUATE, MASTER, PhD, GRADUATE}));
    }

    private void workField() {
        setWorkField(pickFromExcept(WorkField.values(), new WorkField[]{STUDENT, RETIRED}));
    }

    private void monthlyIncome() {
        setMonthlyIncome(randIntBetween(2200, 4500));
    }

    @Override
    public List<Transaction> makeTransactions(int number, LocalDateTime accountCreation) {
        long accountID = getAccountID();
        long clientID = getClientID();

        List<Transaction> transactions = new ArrayList<>();

        LocalDateTime time = accountCreation;

        // Every month he gets his salary
        while (time.isBefore(now())) {
            time = time.plusMonths(1);
            transactions.add(new Transaction(accountID, clientID, getMonthlyIncome(), time));
        }

        time = accountCreation;

        // He frequently removes small amounts of money for day-to-day expenses
        while (time.isBefore(now())) {
            time = time.plusDays(randIntBetween(1, 10));
            transactions.add(new Transaction(accountID, clientID, -randIntBetween(20, 90), time));
        }

        time = accountCreation;

        // A few times in the year he takes a lot of money for bigger expenses
        while (time.isBefore(now())) {
            time = time.plusMonths(randIntBetween(6, 10)).withDayOfMonth(randIntBetween(1, 28));
            transactions.add(new Transaction(accountID, clientID, -randIntBetween(500, 2200), time));
        }

        return transactions;
    }
}
