package shortener.url.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TimePeriod {

	private static Map<Integer, Integer> times = new HashMap<>() {{
		put(1, 15);
		put(2, 30);
		put(3, 60);
		put(4, 360);
		put(5, 1440);
		put(6, 10080);
	}};

	private TimePeriod() {
	}

	public static int getTimeFromIndex(int index) {
		return Optional.ofNullable(times.get(index)).orElseThrow(IllegalTimePeriodIndex::new);
	}
}
