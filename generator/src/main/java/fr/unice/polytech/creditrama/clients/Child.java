package fr.unice.polytech.creditrama.clients;

import fr.unice.polytech.creditrama.transactions.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.ELEMENTARY_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.JUNIOR_HIGH_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.SINGLE;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;
import static java.time.LocalDateTime.now;

public class Child extends Client {
    public Child() {
        super();
        dateOfBirth(8, 14);
        setMaritalStatus(SINGLE);
        setChildrenCount(0);
        securityNumber();
        weight(getAge());
        height(getAge());
        levelOfSchooling();
        setWorkField(STUDENT);
        setMonthlyIncome(-1);
    }

    private void levelOfSchooling() {
        setLevelOfSchooling(getAge() < 11 ? ELEMENTARY_SCHOOL : JUNIOR_HIGH_SCHOOL);
    }

    @Override
    public List<Transaction> makeTransactions(int number, LocalDateTime accountCreation) {
        long accountID = getAccountID();
        long clientID = getClientID();

        List<Transaction> transactions = new ArrayList<>();

        LocalDate dateOfBirth = getDateOfBirth();
        LocalDateTime birthDay = accountCreation
                .withMonth(dateOfBirth.getMonth().getValue())
                .withDayOfMonth(dateOfBirth.getDayOfMonth());

        LocalDateTime time = LocalDateTime.from(birthDay);

        if (birthDay.isAfter(accountCreation)) {
            time = LocalDateTime.from(birthDay.plusDays(randIntBetween(1, 10)));
            double amount = randIntBetween(5, 15) * 10;
            transactions.add(new Transaction(accountID, clientID, amount, time));
        }

        while (time.isBefore(now())) {
            time = time.plusYears(1).withDayOfYear(birthDay.getDayOfYear() + randIntBetween(1, 10));
            double amount = randIntBetween(5, 15) * 10;
            transactions.add(new Transaction(accountID, clientID, amount, time));

            amount = randIntBetween(5, 15) * 10;
            transactions.add(
                    new Transaction(accountID, clientID, amount, LocalDateTime.of(
                            LocalDate.of(time.getYear(), 12, 25),
                            LocalTime.now())
                    )
            );

            if (transactions.size() > amount)
                return transactions;
        }

        return transactions;
    }
}
