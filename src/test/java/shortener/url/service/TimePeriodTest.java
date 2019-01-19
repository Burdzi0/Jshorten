package shortener.url.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimePeriodTest {

	@Test
	void getTimeFromIndex() {
		assertEquals(15, TimePeriod.getTimeFromIndex(1));
		assertEquals(30, TimePeriod.getTimeFromIndex(2));
		assertEquals(60, TimePeriod.getTimeFromIndex(3));
		assertEquals(360, TimePeriod.getTimeFromIndex(4));
		assertEquals(1440, TimePeriod.getTimeFromIndex(5));
		assertEquals(10080, TimePeriod.getTimeFromIndex(6));

		assertThrows(IllegalTimePeriodIndex.class, () -> TimePeriod.getTimeFromIndex(0));
		assertThrows(IllegalTimePeriodIndex.class, () -> TimePeriod.getTimeFromIndex(7));
	}
}