package net.phyokyaw.jaquapi;

import java.util.concurrent.ScheduledFuture;


public interface ScheduledService {
	ScheduledFuture<?> addSchedule(Runnable runnable, long milliSec);
}
