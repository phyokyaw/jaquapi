package net.phyokyaw.jaquapi.dao.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;

public class DateTimeMode extends Mode {

	private final DaySchedule[] schedule;

	public DateTimeMode(LocalTime startTime, LocalTime endTime) {
		schedule = new DaySchedule[]{new DaySchedule(new OnOfftime[]{new OnOfftime(startTime, endTime)})};
	}

	public DateTimeMode(OnOfftime... daySchedules) {
		schedule = new DaySchedule[]{new DaySchedule(daySchedules)};
	}

	public DateTimeMode(DaySchedule... daysOfWeekSchedules) {
		schedule = daysOfWeekSchedules;
	}

	@Override
	public boolean shouldBeOn() {
		for (DaySchedule entry : schedule) {
			if (entry.isInTime()) {
				return true;
			}
		}
		return false;
	}

	public static class DaySchedule {

		private final OnOfftime[] schedule;
		private final int[] daysOfWeek;

		public DaySchedule(OnOfftime[] schedule) {
			daysOfWeek = new int[]{DateTimeConstants.SUNDAY,
					DateTimeConstants.MONDAY,
					DateTimeConstants.TUESDAY,
					DateTimeConstants.WEDNESDAY,
					DateTimeConstants.THURSDAY,
					DateTimeConstants.FRIDAY,
					DateTimeConstants.SATURDAY};
			this.schedule = schedule;
		}

		public DaySchedule(OnOfftime[] schedule, int... daysToInclude) {
			daysOfWeek = daysToInclude;
			this.schedule = schedule;
		}

		public boolean isInTime() {
			DateTime now = DateTime.now();
			for (int i : daysOfWeek) {
				if (i == now.getDayOfWeek()) {
					for (OnOfftime entry : schedule) {
						if (entry.isInTime()) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	public static class OnOfftime {
		private final LocalTime startTime;
		private final LocalTime endTime;

		public OnOfftime(LocalTime startTime, LocalTime endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		public boolean isInTime() {
			LocalTime now = LocalTime.now();
			return (now.isAfter(startTime) && now.isBefore(endTime));
		}
	}
}
