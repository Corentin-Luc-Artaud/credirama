package fr.unice.polytech.creditrama.clients;

import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.SENIOR_HIGH_SCHOOL;
import static fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling.UNDERGRADUATE;
import static fr.unice.polytech.creditrama.clients.enums.MaritalStatus.SINGLE;
import static fr.unice.polytech.creditrama.clients.enums.WorkField.STUDENT;

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

    private void monthlyIncome(){
        setMonthlyIncome(randIntBetween(400, 800));
    }
}
