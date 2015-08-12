package net.phyokyaw.jaquapi.core.services;

import java.util.concurrent.ScheduledFuture;


public interface ScheduledService {
	ScheduledFuture<?> addScheduleAtFixrate(Runnable runnable, long milliSec);
	ScheduledFuture<?> addScheduleAtFixrate(long initialDelay, Runnable runnable, long milliSec);
	ScheduledFuture<?> addSchedule(long delay, Runnable runnable);
}
