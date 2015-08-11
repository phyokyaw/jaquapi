package net.phyokyaw.jaquapi.services;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.dao.TemperatureDao;
import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;
import net.phyokyaw.jaquapi.services.AquaService;
import net.phyokyaw.jaquapi.services.ScheduledService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("temperature")
public class TemperatureService implements AquaService {
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);
	private static final String TEMP_FILE_NAME = "/w1_slave";
	//private static final String TEMP_FILE_PATH = "/sys/bus/w1/devices/28-0000054b468a";
	private static final String TEMP_FILE_PATH = "src/test/resources";

	@Autowired
	private ScheduledService scheduledService;
	@Autowired
	private TemperatureDao dao;
	private double value = 0.0;

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
		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 1000 * 5); //5s
		recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 1000 * 5); //5s
	}

	private void update() {
		logger.debug("Updating temp value");
		String file = getTempFilePath() + TEMP_FILE_NAME;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(file));
			String fileData = Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
			Pattern pattern = Pattern.compile(".*t=(\\d+)");
			Matcher matcher = pattern.matcher(fileData);
			if (matcher.find()) {
				value = Double.parseDouble(matcher.group(1)) / 1000;
			} else {
				throw new Exception("Unable to temp in file");
			}
		} catch (Exception e) {
			logger.error("Unable to read temp", e);
		}
	}

	private String getTempFilePath() {
		return TEMP_FILE_PATH;
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

	public List<TemperatureRecord> getLastRecords(int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		if (days > 1) {
			calendar.add(Calendar.DAY_OF_MONTH, days * -1);
		}
		return dao.findByDate(calendar.getTime());
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
}
