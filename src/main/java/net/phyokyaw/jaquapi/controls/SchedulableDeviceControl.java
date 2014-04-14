package net.phyokyaw.jaquapi.controls;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulableDeviceControl extends DeviceControl {

	private static Logger logger = LoggerFactory.getLogger(SchedulableDeviceControl.class);

	private final ScheduleOnOff[] schedule;

	public SchedulableDeviceControl(Device[] devices, final ScheduleOnOff[] schedule) {
		super(devices);
		this.schedule = schedule;
	}

	@Override
	public void activate() throws Exception {
		super.activate();
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Date date = new Date();
				boolean shouldBeOn = false;
				for (ScheduleOnOff onOff : schedule) {
					if (date.after(onOff.getBeginOn()) && date.before(onOff.getEndOn())) {
						shouldBeOn = true;
						break;
					}
				}
				logger.info("Setting device active to be " + shouldBeOn);
				// i2CDevice.setOn(shouldBeOn);
			}
		}, 0L, 5 * 1000, TimeUnit.MILLISECONDS);
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
