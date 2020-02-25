package fr.unice.polytech.si5.al.TimeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@SpringBootTest
class TimeServiceApplicationTests {
    @Autowired
    private TimeServiceRest service;

    @Test
    public void testTimeService() {
        long date = System.currentTimeMillis();
        long newDate = date-(1*60*60*1000);
        String res = service.setTime(date);
        assertEquals(date, service.getTime());
        assertEquals("OK", res);
        res = service.setTime(newDate);
        assertEquals(newDate, service.getTime());
        assertEquals("OK", res);
    }

    //@Test
    public void testFailOfTimeService() {
        double mean = -1.0;
        Map<Integer, Integer> results = new HashMap<>();
        for (int n = 0; n < 10000; ++n) {
            long date = System.currentTimeMillis();
            long res = date;
            service.setTime(date);
            int cpt = 0;
            while (res == date) {
                res = service.getTimeWithFail();
                ++cpt;
                //if (cpt >= 20) fail();
            }
            Logger.getAnonymousLogger().info("fail at "+cpt);

            Integer value = results.get(cpt);
            if (value == null) {
                results.put(cpt, 1);
            }else {
                results.put(cpt, value+1);
            }

            if (mean < 0) {
                mean = cpt;
            } else {
                mean = (mean + cpt)/2;
            }
            Logger.getAnonymousLogger().info("mean is" + mean);
        }
        assertTrue(mean < 20);
    }
}
