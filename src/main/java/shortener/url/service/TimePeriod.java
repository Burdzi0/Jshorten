package shortener.url.service;

import java.util.ArrayList;
import java.util.Optional;

public class TimePeriod {

	private static ArrayList<Time> list = new ArrayList<>() {{
		add(new Time(1, 15));
		add(new Time(2, 30));
		add(new Time(3, 60));
		add(new Time(4, 360));
		add(new Time(4, 1440));
		add(new Time(5, 10080));
	}};

	private TimePeriod() {
	}

	public static Optional<Integer> getTimeFromIndex(int index) {
		return Optional.ofNullable(list.get(index)).map(Time::getTimeInMinutes);
	}

	private static class Time {

		private int index;
		private int timeInMinutes;

		Time(int index, int timeInMinutes) {
			this.index = index;
			this.timeInMinutes = timeInMinutes;
		}

		public int getIndex() {
			return index;
		}

		public int getTimeInMinutes() {
			return timeInMinutes;
		}
	}
}
