package net.phyokyaw.jaquapi.dao.model;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class SchedulableControl {

	private static Logger logger = LoggerFactory.getLogger(SchedulableControl.class);

	private I2CDevice i2CDevice;

	public void setDevice(GpioPinDigitalOutput pin) {
		i2CDevice = new I2CDevice(pin);
	}

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
	private ScheduledFuture<?> scheduleService;

	public void activate(final ScheduleOnOff[] schedule) {
		scheduleService = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Date date = new Date();
				boolean shouldBeOn = false;
				for (ScheduleOnOff onOff : schedule) {
					logger.info("on " + onOff.getBeginOn());
					logger.info("off " + onOff.getEndOn());
					logger.info("current " + date);
					if (date.after(onOff.getBeginOn()) && date.before(onOff.getEndOn())) {
						shouldBeOn = true;
						break;
					}
				}
				logger.info("Device on should be " + shouldBeOn);
				// i2CDevice.setOn(shouldBeOn);
			}
		}, 0L, 5 * 1000, TimeUnit.MILLISECONDS);
	}

	public void deactivate() {
		scheduleService.cancel(false);
	}

	public static class ScheduleOnOff {
		private Date beginOn;
		private Date endOn;

		public ScheduleOnOff(Date beginOn, Date endOn) {
			super();
			this.setBeginOn(beginOn);
			this.setEndOn(endOn);
		}

		public Date getBeginOn() {
			return beginOn;
		}

		public void setBeginOn(Date beginOn) {
			this.beginOn = beginOn;
		}

		public Date getEndOn() {
			return endOn;
		}

		public void setEndOn(Date endOn) {
			this.endOn = endOn;
		}
	}

	public static Date createTime(int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,minute);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
}
