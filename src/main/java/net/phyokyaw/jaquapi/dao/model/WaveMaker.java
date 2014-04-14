package net.phyokyaw.jaquapi.dao.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import net.phyokyaw.jaquapi.controls.Device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaveMaker extends Device {
	public WaveMaker(String name) {
		super(name);
	}

	private static Logger logger = LoggerFactory.getLogger(WaveMaker.class);
	private static final int CHECK_FOR_FINISH_INTERVAL = 500;
	private static final long REST_TIME = 3000L;

	private final AtomicBoolean isReady = new AtomicBoolean(false);

	private ScheduledExecutorService scheduledExecutorService;

	public void activate(double min_on, double max_on) {
		scheduledExecutorService = Executors.newScheduledThreadPool(3);
		double randNumber = Math.random();
		final long runFor = (long) (((randNumber * (max_on - min_on)) + min_on) * 1000);
		start(runFor);
		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				if (isReady.get()) {
					start(runFor);
				}
			}
		}, CHECK_FOR_FINISH_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public void deactivate() throws InterruptedException {
		if (scheduledExecutorService != null) {
			scheduledExecutorService.shutdown();
			scheduledExecutorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		}
	}

	private void start(long stopTime) {
		//i2CDevice.setOn(true);
		isReady.set(false);
		if (stopTime > 0) {
			scheduledExecutorService.schedule(new Runnable() {
				@Override
				public void run() {
					stop();
				}
			}, stopTime, TimeUnit.MILLISECONDS);
		}
		logger.info("Started " + this.getName() + " will stop in " + stopTime / 1000 +"s");
	}

	private void stop() {
		//i2CDevice.setOn(false);
		logger.info("Stopped " + this.getName() + " resting");
		try {
			Thread.sleep(REST_TIME);
		} catch (InterruptedException e) {
			logger.warn("Thread interrupted");
		} finally {
			isReady.set(true);
		}
		logger.info("Rest finished " + this.getName());
	}

	public boolean isReady() {
		return isReady.get();
	}
}
