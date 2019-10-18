package fr.unice.polytech.si5.al.TimeService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeServiceApplicationTests {
	@Autowired
	private TimeServiceRest service;

	@Test
	void contextLoads() {
	}

	@Test
	public void testTimeService() {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime newDate = date.minusDays(1);
		service.setTime(date);
		assertEquals(date, service.getTime());
		service.setTime(newDate);
		assertEquals(newDate, service.getTime());
	}

}
