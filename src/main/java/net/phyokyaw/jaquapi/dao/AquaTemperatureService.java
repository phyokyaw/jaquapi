package net.phyokyaw.jaquapi.dao;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AquaTemperatureService implements TemperatureService {
	private static Logger logger = LoggerFactory.getLogger(AquaTemperatureService.class);

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	private double value = 0.0;

	@Autowired
	private TemperatureDao dao;

	@PostConstruct
	private void setup() {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 0L, 1L, TimeUnit.MINUTES);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 0L, 5L, TimeUnit.MINUTES);
	}

	private void update() {
		logger.info("Updating value");
		value = 0.0;
	}

	private void record() {
		logger.info("Saving value");
		TemperatureRecord record = new TemperatureRecord();
		record.setValue(value);
		dao.save(record);
	}

	@Override
	public double getValue() {
		return value;
	}
}
