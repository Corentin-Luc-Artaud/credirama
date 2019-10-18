package fr.unice.polytech.creditrama.clients;

import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.ELEMENTARY_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.JUNIOR_HIGH_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.SINGLE;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;

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
}
