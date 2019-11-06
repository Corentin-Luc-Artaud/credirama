package fr.unice.polytech.creditrama;

import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class Utils {

    public static int randIntBetween(int min, int max) {
        return current().nextInt(min, max);
    }

    public static <T> T pickFrom(T[] array) {
        return array[randIntBetween(0, array.length - 1)];
    }

    public static <T> T pickFromExcept(T[] array, T[] exceptions) {
        T extracted;

        do {
            extracted = array[randIntBetween(0, array.length - 1)];
        } while (Arrays.asList(exceptions).contains(extracted));

        return extracted;
    }

    public static <T> T pickFrom(List<T> list) {
        return list.get(randIntBetween(0, list.size() - 1));
    }
}
