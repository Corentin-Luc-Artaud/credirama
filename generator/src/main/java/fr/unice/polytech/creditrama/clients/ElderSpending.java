package fr.unice.polytech.creditrama.clients;

import fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling;
import fr.unice.polytech.creditrama.clients.enums.MaritalStatus;
import fr.unice.polytech.creditrama.transactions.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.unice.polytech.creditrama.Utils.pickFrom;
import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.*;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.*;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.RETIRED;
import static java.time.LocalDateTime.now;

public class ElderSpending extends Client {

    public ElderSpending() {
        super();
        dateOfBirth(60, 90);
        maritalStatus();
        childrenCount();
        securityNumber();
        weight(getAge());
        height(getAge());
        levelOfSchooling();
        setWorkField(RETIRED);
        monthlyIncome();
    }

    private void maritalStatus() {
        setMaritalStatus(pickFrom(new MaritalStatus[]{SINGLE, MARRIED}));
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

    private void monthlyIncome() {
        setMonthlyIncome(randIntBetween(900, 1400));
    }

    @Override
    public List<Transaction> makeTransactions(int number, LocalDateTime accountCreation) {
        long accountID = getAccountID();
        long clientID = getClientID();

        List<Transaction> transactions = new ArrayList<>();

        LocalDateTime time = accountCreation;

        double savings = randIntBetween(90, 250) * 1000;
        double spent = 0;

        // The transfer their savings on our bank
        transactions.add(new Transaction(accountID, clientID, savings, time));

        // Every month they earn their pension
        while (time.isBefore(now())) {
            time = time.plusMonths(1);
            transactions.add(new Transaction(accountID, clientID, getMonthlyIncome(), time));
        }

        time = accountCreation;

        // The spend twice more than they earn !
        while (time.isBefore(now()) && ((savings - 2000) > spent)) {
            int months = randIntBetween(2, 5);
            time = time.plusMonths(months);

            double amount = -2 * months * getMonthlyIncome();
            transactions.add(new Transaction(accountID, clientID, amount, time));
        }

        return transactions;
    }
}
