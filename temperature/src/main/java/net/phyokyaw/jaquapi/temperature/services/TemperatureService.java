
package net.phyokyaw.jaquapi.temperature.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.services.AquaService;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.remote.MessageListener;
import net.phyokyaw.jaquapi.remote.RemoteMessagingService;
import net.phyokyaw.jaquapi.temperature.dao.TemperatureDao;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;

@Service("temperature")
public class TemperatureService implements AquaService, MessageListener {
	private static final String topic = "/fishtank/temperature";
	private static final String primary = topic + "/28-031466113fff";
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private RemoteMessagingService messagingService;

	@Autowired
	private TemperatureDao dao;
	private double value = 0.0;
	private DateTime lastRecorded;

	private ScheduledFuture<?> recordSchedule;

	@PreDestroy
	private void shutdown() {
		if (recordSchedule != null) {
			recordSchedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		messagingService.addMessageListener(primary, this);
	}

	private void record() {
		try {
			TemperatureRecord record = new TemperatureRecord();
			record.setValue(value);
			dao.save(record);
			logger.info("Saving temp value: " + record.getValue());
		} catch(Exception ex) {
			logger.error("Unable to save", ex);
		}
	}


	public List<TemperatureRecord> getLastRecords(HistoryInterval days) {
		Calendar calendar = new GregorianCalendar();

		List<TemperatureRecord> filteredRecords = new ArrayList<TemperatureRecord>();
		if (days == HistoryInterval.HOUR) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			List<TemperatureRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 5) {
				if (i < records.size()) {
					filteredRecords.add(records.get(i));
				}
			}
		} else if (days == HistoryInterval.DAY) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.add(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			List<TemperatureRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 60) {
				if (i < records.size()) {
					filteredRecords.add(records.get(i));
				}
			}
		} else if (days == HistoryInterval.WEEK) {
			Calendar start = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Pageable one = new PageRequest(0, 1);
			for (int i = 0; i < 7; i++) {
				List<TemperatureRecord> result = dao.findByDate(calendar.getTime(), start.getTime(), one);
				if (result == null || result.isEmpty()) {
					break;
				}
				filteredRecords.addAll(result);
				start.add(Calendar.DAY_OF_MONTH, -1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}
		}
		return filteredRecords;
	}


	public TemperatureRecord getTemperature() {
		TemperatureRecord temp = new TemperatureRecord();
		temp.setValue(this.getValue());
		temp.setStoredTime(new Date());
		return temp;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void messageArrived(String topic, String message) {
		value = Double.parseDouble(message);
		lastRecorded = new DateTime();
	}

	@Override
	public void sensorDeviceConnection(boolean sensorAvailable) {
		if (sensorAvailable) {
			recordSchedule = scheduledService.addScheduleAtFixrate(1000 * 20, new Runnable() {
				@Override
				public void run() {
					if (lastRecorded != null) {
						Interval interval = new Interval(lastRecorded, new DateTime());
						if (interval.toDuration().getStandardMinutes() > 2) {
							logger.error("Temperature data not updated for last " + interval.toDuration().getStandardHours() + " hours");
						} else {
							record();
						}
					}
				}
			}, 1000 * 60);
		} else {
			if (recordSchedule != null) {
				recordSchedule.cancel(false);
			}
			lastRecorded = null;
		}
	}
}
