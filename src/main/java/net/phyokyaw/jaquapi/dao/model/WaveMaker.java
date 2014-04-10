package net.phyokyaw.jaquapi.dao.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class WaveMaker {
	private static Logger logger = LoggerFactory.getLogger(WaveMaker.class);
	private static final int CHECK_FOR_FINISH_INTERVAL = 500;
	private static final long REST_TIME = 3000L;

	private final String name;

	private final AtomicBoolean isFinished = new AtomicBoolean(false);

	private ScheduledFuture<?> runner;

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	private I2CDevice i2CDevice;

	public WaveMaker(String name) {
		this.name = name;
	}

	public void setDevice(GpioPinDigitalOutput pin) {
		i2CDevice = new I2CDevice(pin);
	}

	public String getName() {
		return name;
	}

	public void activate(double min_on, double max_on) {
		double randNumber = Math.random();
		final long runFor = (long) (((randNumber * (max_on - min_on)) + min_on) * 1000);
		start(runFor);
		runner = scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				if (isFinished.get()) {
					start(runFor);
				}
			}
		}, CHECK_FOR_FINISH_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public void deactivateAndStop() {
		if (runner != null) {
			runner.cancel(false);
			off();
		}
	}

	public void on() {
		start(0L);
	}

	public void start(long stopTime) {
		i2CDevice.getPin().setState(PinState.LOW);
		isFinished.set(false);
		if (stopTime > 0) {
			scheduledExecutorService.schedule(new Runnable() {
				@Override
				public void run() {
					off();
				}
			}, stopTime, TimeUnit.MILLISECONDS);
		}
		logger.info("Started " + this.getName() + " will stop in " + stopTime / 1000 +"s");
	}

	public void off() {
		i2CDevice.getPin().setState(PinState.HIGH);
		logger.info("Stopped " + this.getName() + " resting");
		try {
			Thread.sleep(REST_TIME);
		} catch (InterruptedException e) {
			logger.warn("Thread interrupted");
		} finally {
			isFinished.set(true);
		}
		logger.info("Rest finished " + this.getName());
	}

	public boolean isFinished() {
		return isFinished.get();
	}
}
