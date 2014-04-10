package net.phyokyaw.jaquapi;

import java.util.concurrent.ScheduledFuture;


public interface ScheduledService {
	ScheduledFuture<?> addScheduleAtFixrate(Runnable runnable, long milliSec);
	ScheduledFuture<?> addSchedule(Runnable runnable);
}
