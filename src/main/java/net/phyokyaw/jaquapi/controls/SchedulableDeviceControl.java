package net.phyokyaw.jaquapi.controls;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulableDeviceControl extends DeviceControl {

	private static Logger logger = LoggerFactory.getLogger(SchedulableDeviceControl.class);

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
	private ScheduledFuture<?> scheduleService;
	private final ScheduleOnOff[] schedule;

	public SchedulableDeviceControl(Device[] devices, final ScheduleOnOff[] schedule) {
		super(devices);
		this.schedule = schedule;
	}

	@Override
	public void activate() throws Exception {
		super.activate();
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
				if (!Thread.interrupted())
				{
					logger.info("Setting device active to be " + shouldBeOn);
					// i2CDevice.setOn(shouldBeOn);
				}
			}
		}, 0L, 5 * 1000, TimeUnit.MILLISECONDS);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		scheduleService.cancel(false);
		try {
			scheduledExecutorService.awaitTermination(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Schedule deactivated");
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
