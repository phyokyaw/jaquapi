package net.phyokyaw.jaquapi.dao.model;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalTime;


public class DateTimeMode extends Mode {

	private final DaySchedule[] schedule;
	private static ObjectMapper mapper = new ObjectMapper();

	public DateTimeMode(int startHour, int startMin, int endHour, int endMin) {
		schedule = new DaySchedule[]{new DaySchedule(new OnOfftime[]{new OnOfftime(startHour, startMin, endHour, endMin)})};
	}

	public DateTimeMode(OnOfftime... daySchedules) {
		schedule = new DaySchedule[] {new DaySchedule(daySchedules)};
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
			daysOfWeek = new int[] {DateTimeConstants.SUNDAY,
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

		@JsonIgnore
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
		private final int startHour;
		private final int startMin;
		private final int endHour;
		private final int endMin;

		@JsonIgnore
		private final LocalTime startTime;
		@JsonIgnore
		private final LocalTime endTime;

		public OnOfftime(int startHour, int startMin, int endHour, int endMin) {
			this.startHour = startHour;
			this.endHour = endHour;
			this.startMin = startMin;
			this.endMin = endMin;
			startTime = LocalTime.parse(this.startHour + ":" + this.startMin);
			endTime = LocalTime.parse(this.endHour + ":" + this.endMin);
		}

		@JsonIgnore
		public boolean isInTime() {
			LocalTime now = LocalTime.now();
			return (now.isAfter(startTime) && now.isBefore(endTime));
		}
	}

	public static DateTimeMode ceateDateTimeMode(String jsonString) throws Exception {
		return mapper.readValue(jsonString, DateTimeMode.class);
	}

	public String toJson() throws Exception {
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}
}
