package net.phyokyaw.jaquapi;

import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.dao.PhDao;
import net.phyokyaw.jaquapi.dao.model.PhRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ph")
public class PhService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(PhService.class);
	private static String PH_READER_PROC = "phreader.py";
	private double value = 4.0;

	@Autowired
	private ScheduledService scheduledService;
	@Autowired
	private PhDao dao;

	private ScheduledFuture<?> updateSchedule;
	private ScheduledFuture<?> recordSchedule;

	@PreDestroy
	private void shutdown() {
		if (updateSchedule != null) {
			updateSchedule.cancel(false);
		}
		if (recordSchedule != null) {
			recordSchedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		updateSchedule = scheduledService.addSchedule(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 1000 * 5); //5s
		recordSchedule = scheduledService.addSchedule(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 1000 * 5); //5s
	}

	private void update() {
		logger.info("Updating ph value");
		value = 5.0;
	}

	private void record() {
		logger.info("Saving ph value");
		PhRecord record = new PhRecord();
		record.setValue(value);
		dao.save(record);
	}

	public PhRecord getPhRecord() {
		PhRecord ph = new PhRecord();
		ph.setValue(getValue());
		return ph;
	}

	@Override
	public double getValue() {
		return value;
	}
}
