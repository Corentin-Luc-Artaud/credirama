package fr.unice.polytech.si5.al.TimeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TimeServiceApplicationTests {
    @Autowired
    private TimeServiceRest service;

    @Test
    public void testTimeService() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime newDate = date.minusDays(1);
        String res = service.setTime(date.toString());
        assertEquals(date, service.getTime());
        assertEquals("OK", res);
        res = service.setTime(newDate.toString());
        assertEquals(newDate, service.getTime());
        assertEquals("OK", res);
    }
}
