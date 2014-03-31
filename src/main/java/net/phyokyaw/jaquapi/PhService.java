package net.phyokyaw.jaquapi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.dao.PhDao;
import net.phyokyaw.jaquapi.dao.model.PhRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ph")
public class PhService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(PhService.class);

	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

	private double value = 0.0;

	@Autowired
	private PhDao dao;

	@PostConstruct
	private void setup() {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 0L, 3L, TimeUnit.MINUTES);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 0L, 5L, TimeUnit.MINUTES);
	}

	private void update() {
		logger.info("Updating value");
		value = 5.0;
	}

	private void record() {
		logger.info("Saving value");
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
