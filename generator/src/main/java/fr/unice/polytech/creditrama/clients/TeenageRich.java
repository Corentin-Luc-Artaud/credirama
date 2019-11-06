package fr.unice.polytech.creditrama.clients;

import fr.unice.polytech.creditrama.transactions.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.SENIOR_HIGH_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.UNDERGRADUATE;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.SINGLE;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;
import static java.time.LocalDateTime.now;

public class TeenageRich extends Client {

    public TeenageRich() {
        super();
        dateOfBirth(16, 19);
        setMaritalStatus(SINGLE);
        setChildrenCount(0);
        securityNumber();
        weight(getAge());
        height(getAge());
        levelOfSchooling();
        setWorkField(STUDENT);
        monthlyIncome();
    }

    private void levelOfSchooling() {
        setLevelOfSchooling(getAge() <= 18 ? SENIOR_HIGH_SCHOOL : UNDERGRADUATE);
    }

    private void monthlyIncome() {
        setMonthlyIncome(randIntBetween(400, 800));
    }

    @Override
    public List<Transaction> makeTransactions(int number, LocalDateTime accountCreation) {
        long accountID = getAccountID();
        long clientID = getClientID();

        List<Transaction> transactions = new ArrayList<>();

        LocalDateTime time = accountCreation;

        // Every 6 month his parents give him money as pocket-money !
        while (time.isBefore(now())) {
            time = time.plusMonths(6);
            transactions.add(new Transaction(accountID, clientID, getMonthlyIncome() * 6, time));
        }

        time = accountCreation;

        // Sometimes he takes 200$ from his account to spend on restaurant and cinema
        while (time.isBefore(now())) {
            time = time.plusMonths(randIntBetween(1, 8)).withDayOfMonth(randIntBetween(1, 28));
            transactions.add(new Transaction(accountID, clientID, -200, time));
        }

        return transactions;
    }
}
