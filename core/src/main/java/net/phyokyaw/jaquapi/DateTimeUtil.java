package net.phyokyaw.jaquapi;

import java.util.Calendar;

public class DateTimeUtil {

	public static Calendar firstDayOfLastWeek(Calendar c)
	{
		c = (Calendar) c.clone();
		// last week
		c.add(Calendar.WEEK_OF_YEAR, -1);
		// first day
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c;
	}

	public static Calendar lastDayOfLastWeek(Calendar c)
	{
		c = (Calendar) c.clone();
		// first day of this week
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		// last day of previous week
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c;
	}
}
