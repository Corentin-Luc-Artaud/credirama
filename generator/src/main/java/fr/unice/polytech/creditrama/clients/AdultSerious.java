package fr.unice.polytech.creditrama.clients;

import fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling;
import fr.unice.polytech.creditrama.clients.enums.MaritalStatus;
import fr.unice.polytech.creditrama.clients.enums.WorkField;

import java.util.Arrays;

import static fr.unice.polytech.creditrama.Utils.*;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.*;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.*;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.RETIRED;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;

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
}
