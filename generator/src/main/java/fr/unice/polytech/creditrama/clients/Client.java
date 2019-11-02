package fr.unice.polytech.creditrama.clients;

import com.github.javafaker.Faker;
import fr.unice.polytech.creditrama.clients.enums.LevelOfSchooling;
import fr.unice.polytech.creditrama.clients.enums.MaritalStatus;
import fr.unice.polytech.creditrama.clients.enums.Nationality;
import fr.unice.polytech.creditrama.clients.enums.WorkField;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import static fr.unice.polytech.creditrama.Utils.pickFrom;
import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static fr.unice.polytech.creditrama.clients.enums.Nationality.FR;
import static java.time.LocalDate.now;
import static java.time.LocalDate.ofYearDay;

@Getter
@Setter
@With
@AllArgsConstructor
@EqualsAndHashCode
public class Client {

    private static final Faker faker = new Faker();

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Nationality nationality;
    private String zipCode;
    private String city;
    private String countryOfResidence;
    private String fiscalNumber;
    private MaritalStatus maritalStatus;
    private int childrenCount = 0;
    private String counselorName;
    private String securityNumber;
    private double weight;
    private double height;
    private LevelOfSchooling levelOfSchooling;
    private WorkField workField;
    private double monthlyIncome;

    /**
     * nationality(); <br>
     * fiscalNumber(); <br>
     * countryOfResidence();
     */
    Client() {
        names();
        nationality();
        fiscalNumber();
        countryOfResidence();
        location();
    }

    int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    void dateOfBirth(int min, int max) {
        int age = randIntBetween(min, max);
        int dayOfYear = randIntBetween(1, 365);
        int currentYear = now().getYear();

        LocalDate date = ofYearDay(currentYear - age, dayOfYear);
        setDateOfBirth(date);
    }

    private void names() {
        setFirstName(faker.name().firstName());
        setLastName(faker.name().lastName());
        setCounselorName(faker.name().fullName());
    }

    private void nationality() {
        setNationality(pickFrom(Nationality.values()));
    }

    private void countryOfResidence() {
        int random = randIntBetween(0, 100);

        Nationality nationality = (random < 90) ? getNationality() : pickFrom(Nationality.values());
        setCountryOfResidence(nationality.getCountryName());
    }

    private void location() {

        // Map <CityName, ZipCode>
        Map<String, String> cities = nationality.getCities();

        String city = pickFrom(new ArrayList<>(cities.keySet()));

        setCity(city);
        setZipCode(cities.get(city));
    }

    private void fiscalNumber() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            builder.append(randIntBetween(0, 9));
        }

        setFiscalNumber(builder.toString());
    }

    /**
     * securityNumber - [ 1-sex 2-year 2-month 2-random (99 if not Fr) 3-random (0-120) 3-random 2-random ]
     */
    void securityNumber() {
        String builder = randIntBetween(1, 2) + // random sex
                dateOfBirth.format(DateTimeFormatter.ofPattern("YY")) +
                dateOfBirth.format(DateTimeFormatter.ofPattern("MM")) +
                (getNationality() == FR ? randIntBetween(1, 98) : 99) +
                randIntBetween(100, 999) +
                randIntBetween(100, 999) +
                randIntBetween(10, 99);

        setSecurityNumber(builder);
    }

    void weight(int age) {
        if (age > 8 && age <= 11)
            setWeight(randIntBetween(20, 40));
        else if (age > 11 && age <= 13)
            setWeight(randIntBetween(25, 60));
        else if (age > 13 && age <= 16)
            setWeight(randIntBetween(35, 70));
        else if (age > 16)
            setWeight(randIntBetween(50, 80));
    }

    void height(int age) {
        if (age > 8 && age <= 11)
            setHeight(randIntBetween(100, 140));
        else if (age > 11 && age <= 13)
            setHeight(randIntBetween(115, 170));
        else if (age > 13 && age <= 16)
            setHeight(randIntBetween(125, 185));
        else if (age > 16)
            setHeight(randIntBetween(150, 195));
    }

    @Override
    public String toString() {
        return '{' +
                "\"firstName\":\"" + firstName + '\"' +
                "\"lastName\":\"" + lastName + '\"' +
                "\"city\":\"" + city + '\"' +
                "\"weight\":" + weight +
                "\"height\":" + height +
                '}';
    }
}
