package net.phyokyaw.jaquapi.core.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class ScheduledExcutionService implements ScheduledService {
	private static Logger logger = LoggerFactory.getLogger(ScheduledExcutionService.class);
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

	public ScheduledExcutionService() {
		logger.info("Service created");
	}

	@PreDestroy
	private void shutdown() {
		scheduledExecutorService.shutdown();
	}

	@Override
	public ScheduledFuture<?> addScheduleAtFixrate(Runnable runnable, long milliSec) {
		return scheduledExecutorService.scheduleAtFixedRate(runnable, 0L, milliSec, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture<?> addSchedule(long delay, Runnable runnable) {
		return scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture<?> addScheduleAtFixrate(long initialDelay,
			Runnable runnable, long milliSec) {
		return scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, milliSec, TimeUnit.MILLISECONDS);
	}
}
