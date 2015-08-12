package net.phyokyaw.jaquapi.ph.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.core.services.AquaService;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.ph.dao.PhDao;
import net.phyokyaw.jaquapi.ph.dao.model.PhRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ph")
public class PhService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(PhService.class);
	private static String PH_READER_PROC = "/home/pi/phreader.py";
	private double value = 0.0d;

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
//		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
//			@Override
//			public void run() {
//				update();
//			}
//		}, 1000 * 10); //5s
//		recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
//			@Override
//			public void run() {
//				record();
//			}
//		}, 1000 * 15); //5s
	}

	private void update() {
		try {
			Runtime r = Runtime.getRuntime();
			Process p;
			String line;
			p = r.exec(PH_READER_PROC);
			BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = is.readLine()) != null) {
				value = Double.parseDouble(line);
			}
			System.out.flush();
			p.waitFor(); // wait for process to complete
			logger.info("Updating ph value with: " + value);
		} catch (Exception e) {
			logger.error("Error executing ph reader", e);
		}
	}

	private void record() {
		PhRecord record = new PhRecord();
		record.setValue(value);
		dao.save(record);
		logger.info("Saving ph value: " + record.getValue());
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
