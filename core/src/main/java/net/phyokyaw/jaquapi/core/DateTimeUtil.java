package net.phyokyaw.jaquapi.core;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static Calendar firstDayOfLastWeek(Calendar c) {
		c = (Calendar) c.clone();
		// last week
		c.add(Calendar.WEEK_OF_YEAR, -1);
		// first day
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c;
	}

	public static Calendar lastDayOfLastWeek(Calendar c) {
		c = (Calendar) c.clone();
		// first day of this week
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		// last day of previous week
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c;
	}

	public String getTimeDifference(Date curdate, Date olddate) {
		int years;
		int months;
		int days;
		int hours;
		int minutes;
		int seconds;
		String differenceString = "";
		float diff = curdate.getTime() - olddate.getTime();
		if (diff >= 0) {
			int yearDiff = Math
					.round((diff / (365l * 2592000000f)) >= 1 ? (diff / (365l * 2592000000f))
							: 0);
			if (yearDiff > 0) {
				years = yearDiff;
				differenceString = years + (years == 1 ? " year" : " years")
						+ " ago";
			} else {
				int monthDiff = Math
						.round((diff / 2592000000f) >= 1 ? (diff / 2592000000f)
								: 0);
				if (monthDiff > 0) {
					if (monthDiff > 11)
						monthDiff = 11;

					months = monthDiff;
					differenceString = months
							+ (months == 1 ? " month" : " months") + " ago";
				} else {
					int dayDiff = Math
							.round((diff / (86400000f)) >= 1 ? (diff / (86400000f))
									: 0);
					if (dayDiff > 0) {
						days = dayDiff;
						if (days == 30)
							days = 29;
						differenceString = days
								+ (days == 1 ? " day" : " days") + " ago";
					} else {
						int hourDiff = Math
								.round((diff / (3600000f)) >= 1 ? (diff / (3600000f))
										: 0);
						if (hourDiff > 0) {
							hours = hourDiff;
							differenceString = hours
									+ (hours == 1 ? " hour" : " hours" + " ago");
						} else {
							int minuteDiff = Math
									.round((diff / (60000f)) >= 1 ? (diff / (60000f))
											: 0);
							if (minuteDiff > 0) {
								minutes = minuteDiff;
								differenceString = minutes
										+ (minutes == 1 ? " minute"
												: " minutes") + " ago";
							} else {
								int secondDiff = Math
										.round((diff / (1000f)) >= 1 ? (diff / (1000f))
												: 0);
								if (secondDiff > 0)
									seconds = secondDiff;
								else
									seconds = 1;
								differenceString = seconds
										+ (seconds == 1 ? " second"
												: " seconds") + " ago";
							}
						}
					}

				}
			}

		}
		return differenceString;
	}
}