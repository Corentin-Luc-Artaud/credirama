package fr.unice.polytech.creditrama;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

import static fr.unice.polytech.creditrama.Utils.randIntBetween;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.math.NumberUtils.max;

public class TimeFactory {

    public static LocalDateTime generateTimeStamp(LocalDate birth) {

        long minimum = now().minusYears(8).toLocalDate().toEpochDay();
        long maximum = now().minusYears(3).toLocalDate().toEpochDay();

        minimum = max(minimum, birth.plusYears(5).toEpochDay());

        if (maximum < minimum)
            maximum = now().toLocalDate().toEpochDay();

        if (minimum >= maximum)
            maximum = minimum + 1;

        long randomEpochDay = ThreadLocalRandom.current().longs(minimum, maximum).findAny().getAsLong();

        int hour = randIntBetween(0, 11);
        int minute = randIntBetween(0, 59);

        LocalDateTime time = LocalDateTime.of(LocalDate.ofEpochDay(randomEpochDay), LocalTime.of(hour, minute));

        if (time.getMonth() == Month.JANUARY || time.getMonth() == Month.FEBRUARY)
            return generateTimeStamp(birth);

        return time;
    }

}


///                                           8 years
///                                    5 years        3 years
///       ----------------------|------------------|-----------|------>
///                           birth               min         now