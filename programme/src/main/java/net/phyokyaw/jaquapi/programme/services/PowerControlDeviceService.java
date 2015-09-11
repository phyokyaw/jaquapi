package net.phyokyaw.jaquapi.programme.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.DateTimeUtil;
import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.programme.dao.ProgrammeRecordDao;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.programme.model.Programme.ProgrammeDevice;
import net.phyokyaw.jaquapi.programme.model.ProgrammeRecord;

@Service("programme")
public class PowerControlDeviceService {
	private static final Logger logger = LoggerFactory.getLogger(PowerControlDeviceService.class);
	public static final String PROGRAM_NAME_FEEDING = "Feeding";
	@Resource(name="devices")
	private List<Device> devices;

	@Resource(name="programmes")
	private List<Programme> programmes;

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private ProgrammeRecordDao programmeRecordDao;

	private ScheduledFuture<?> deviceUpdate;

	private Programme activedProgramme;

	@PostConstruct
	public void setup() {
		logger.info("Start scheduling");
		deviceUpdate = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				for (Device device : devices) {
					device.update();
				}
			}
		}, 1000);
	}

	@PreDestroy
	public void shutdown() {
		deviceUpdate.cancel(true);
	}

	public Collection<Programme> getProgrammes() {
		return programmes;
	}

	public void activateProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				if (activedProgramme != null) {
					activedProgramme.deactivate();
				}
				programme.activate();
				activedProgramme = programme;
				storeProgrammeRecord(programme.getId());
				break;
			}
		}
	}

	public Device[] getDevices() {
		return devices.toArray(new Device[]{});
	}

	public Device getDevice(long id) {
		for (Device device : devices) {
			if (device.getId() == id) {
				return device;
			}
		}
		return null;
	}

	public static class DeviceStatus {
		private final boolean on;
		private final long id;
		private final String modeInfo;
		private final long overridingModeTimeout;
		private final String name;
		public DeviceStatus(Device device) {
			id = device.getId();
			modeInfo = device.getMode().getInfo();
			on = device.isOn();
			overridingModeTimeout = device.isOverridingModeScheduleActive() ? device.getOverridingModeTimeOut() : 0;
			name = device.getName();
		}
		public boolean isOn() {
			return on;
		}
		public long getId() {
			return id;
		}
		public String getModeInfo() {
			return modeInfo;
		}
		public String getName() {
			return name;
		}
		public long getOverridingModeTimeout() {
			return overridingModeTimeout;
		}

		public String getOverridingModeTimeoutFormatted() {
			return convertTime(overridingModeTimeout);
		}

		public boolean isOverridden() {
			return overridingModeTimeout > 0;
		}

		private static String convertTime(long millis) {
			long second = (millis / 1000) % 60;
			long minute = (millis / (1000 * 60)) % 60;
			long hour = (millis / (1000 * 60 * 60)) % 24;

			return (hour > 0) ? String.format("%02dh:%02dm:%02ds", hour, minute, second) : String.format("%02dm:%02ds", minute, second);
		}
	}

	public static class ProgrammeStatus {
		private final long id;
		private final String name;
		private final ProgrammeRecord lastRecord;
		private final ProgrammeDevice[] programmeDevices;

		public ProgrammeStatus(Programme programme, ProgrammeRecord lastRecord) {
			name = programme.getName();
			id = programme.getId();
			programmeDevices = programme.getDevices().toArray(new ProgrammeDevice[]{});
			this.lastRecord = lastRecord;
		}
		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getProgrammeStatus() {
			if (lastRecord != null) {
				return "Done " + DateTimeUtil.getTimeDifference(DateTime.now().toDate(), lastRecord.getStoredTime()) + ".";
			}
			return "-";
		}
		public ProgrammeDevice[] getProgrammeDevices() {
			return programmeDevices;
		}
	}

	public Programme getProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				return programme;
			}
		}
		return null;
	}

	public void storeProgrammeRecord(long id) {
		ProgrammeRecord programmeRecord = new ProgrammeRecord();
		programmeRecord.setProgrammeId(id);
		programmeRecordDao.save(programmeRecord);
	}

	public void deactivateProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				for (ProgrammeDevice device : programme.getDevices()) {
					if (device.getDevice().isOverridingModeScheduleActive()) {
						device.getDevice().cancelOverridingMode();
					}
				}
			}
		}
	}

	public ProgrammeStatus[] getProgrammesStatus() {
		List<ProgrammeStatus> status = new ArrayList<ProgrammeStatus>();
		for (Programme programme : programmes) {
			ProgrammeRecord lastRecord = programmeRecordDao.findTopByProgrammeIdOrderByStoredTimeDesc(programme.getId());
			status.add(new ProgrammeStatus(programme, lastRecord));
		}
		return status.toArray(new ProgrammeStatus[]{});
	}


	public ProgrammeStatus getProgrammeStatus(long id) {
		for (ProgrammeStatus programmeStatus : getProgrammesStatus()) {
			if (programmeStatus.getId() == id) {
				return programmeStatus;
			}
		}
		return null;
	}
}
