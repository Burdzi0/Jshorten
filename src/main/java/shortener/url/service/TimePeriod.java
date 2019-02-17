package shortener.url.service;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class TimePeriod {

	private static Map<Integer, Integer> times = ofEntries(
			entry(1, 15),
			entry(2, 30),
			entry(3, 60),
			entry(4, 360),
			entry(5, 1440),
			entry(6, 10080)
	);

	private TimePeriod() {
	}

	public static int getTimeFromIndex(int index) {
		return Optional.ofNullable(times.get(index)).orElseThrow(IllegalTimePeriodIndex::new);
	}
}
