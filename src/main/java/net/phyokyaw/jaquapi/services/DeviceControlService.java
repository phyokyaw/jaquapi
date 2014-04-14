package net.phyokyaw.jaquapi.services;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.controls.Device;
import net.phyokyaw.jaquapi.controls.PauseDeviceControl;
import net.phyokyaw.jaquapi.controls.SchedulableDeviceControl;
import net.phyokyaw.jaquapi.controls.SchedulableDeviceControl.ScheduleOnOff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deviceControl")
public class DeviceControlService {

	private static Logger logger = LoggerFactory.getLogger(DeviceControlService.class);

	@Autowired
	private ScheduledService scheduledService;

	private final Device dev = new Device("Pump");
	@PostConstruct
	public void setup() {
		scheduledService.addSchedule(0L, new Runnable() {
			@Override
			public void run() {
				SchedulableDeviceControl.ScheduleOnOff test1 = new SchedulableDeviceControl.ScheduleOnOff(SchedulableDeviceControl.createTime(9, 51), SchedulableDeviceControl.createTime(13, 34));
				SchedulableDeviceControl test2 = new SchedulableDeviceControl(new Device[]{dev}, new ScheduleOnOff[]{test1});
				PauseDeviceControl test3 = new PauseDeviceControl(new Device[]{dev}, true, 30 * 1000);
				try {
					test2.activate();
					Thread.sleep(10 * 1000);
					test3.activate();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
		//		RandomWaveMakerControl test = new RandomWaveMakerControl(new WaveMaker[]{wm, wm1}, 3, 5, true);
		//		test.activate();

	}
}
