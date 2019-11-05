package fr.unice.polytech.creditrama;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static java.time.LocalDateTime.now;

public class TimeFactory {

    public static LocalDateTime generateTimeStamp(LocalDate birth) {

        long minimum = birth.plusYears(5).toEpochDay();
        long maximum = now().minusYears(3).toLocalDate().toEpochDay();

        if (maximum < minimum)
            maximum = now().toLocalDate().toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(minimum, maximum).findAny().getAsLong();

        int hour = randIntBetween(0, 11);
        int minute = randIntBetween(0, 59);

        return LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.of(hour, minute));
    }

}


///                                           8 years
///                                    5 years        3 years
///       ----------------------|------------------|-----------|------>
///                           birth               min         now