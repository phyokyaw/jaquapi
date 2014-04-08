package net.phyokyaw.jaquapi;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);
	private static final String TEMP_FILE_NAME = "/w1_slave";
	private static final String TEMP_FILE_PATH = "/sys/bus/w1/devices/28-0000054b468a";

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
		return "/scratch/dev/jaquapi/src/test/resources";
	}

	private void record() {
		try {
			logger.debug("Saving temp value");
			TemperatureRecord record = new TemperatureRecord();
			record.setValue(value);
			dao.save(record);
		} catch(Exception ex) {
			logger.error("Unable to save", ex);
		}
	}

	public List<TemperatureRecord> getTodayTempRecord() {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return dao.findByDate(date.getTime());
	}

	public List<TemperatureRecord> getLast7DaysRecord() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		return dao.findByDate(calendar.getTime());
	}

	public List<TemperatureRecord> getLastMonthRecord() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1);
		return dao.findByDate(calendar.getTime());
	}

	@Override
	public double getValue() {
		return value;
	}
}
