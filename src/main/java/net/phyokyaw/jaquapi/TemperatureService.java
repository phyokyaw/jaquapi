package net.phyokyaw.jaquapi;

import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.dao.TemperatureDao;
import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("temperature")
public class TemperatureService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(TemperatureService.class);

	@Autowired
	private ScheduledService scheduledService;
	@Autowired
	private TemperatureDao dao;
	private double value = 0.0;

	private ScheduledFuture<?> schedule;

	@PreDestroy
	private void shutdown() {
		if (schedule != null) {
			schedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		schedule = scheduledService.addSchedule(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 1000 * 5); //5s
	}

	private void update() {
		logger.info("Updating value");
		value = 20.0;
	}

	private void record() {
		logger.info("Saving value");
		TemperatureRecord record = new TemperatureRecord();
		record.setValue(value);
		dao.save(record);
	}

	public TemperatureRecord getTemperatureRecord() {
		TemperatureRecord temp = new TemperatureRecord();
		temp.setValue(getValue());
		return temp;
	}

	@Override
	public double getValue() {
		return value;
	}
}
