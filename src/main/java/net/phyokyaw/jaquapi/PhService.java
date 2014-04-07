package net.phyokyaw.jaquapi;

import net.phyokyaw.jaquapi.dao.PhDao;
import net.phyokyaw.jaquapi.dao.model.PhRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ph")
public class PhService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(PhService.class);
	private double value = 4.0;

	@Autowired
	private PhDao dao;

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
