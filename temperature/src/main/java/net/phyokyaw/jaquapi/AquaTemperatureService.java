package net.phyokyaw.jaquapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AquaTemperatureService implements TemperatureService {

	private static final Logger logger =  LoggerFactory.getLogger(AquaTemperatureService.class);

	private String temperatureFilePath;
	private EntityManager em = null;
	private float value = 0.0f;


	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public float getValue() {
		return value;
	}

	public String getTemperatureFilePath() {
		return temperatureFilePath;
	}

	public void setTemperatureFilePath(String temperatureFilePath) {
		this.temperatureFilePath = temperatureFilePath;
	}

	@Override
	public void record() {
		TemperatureRecord record = new TemperatureRecord();
		record.setValue(value);
		em.persist(record);
		em.flush();
	}

	@Override
	public void update() {
		try {
			value = readFromFile();
			logger.debug("Updated temp value is " + value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to update temperature value", e);
		}
	}

	private static final Pattern pattern = Pattern.compile(".*t=(\\d+)");

	private float readFromFile() throws Exception {
		StringBuilder everything = new StringBuilder();
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(temperatureFilePath));
		try {
			while((line = reader.readLine()) != null) {
				everything.append(line);
			}
			Matcher matcher = pattern.matcher(everything.toString());
			if(matcher.find())
			{
				return Float.parseFloat(matcher.group(1)) / 1000f;
			} else {
				throw new Exception("Unable to find temperature value in the file");
			}
		} finally {
			reader.close();
		}
	}

	public List<TemperatureRecord> getAll() {
		return em.createQuery("select tr from TemperatureRecord tr", TemperatureRecord.class).getResultList();
	}

	public List<TemperatureRecord> getToday() {
		// TODO Auto-generated method stub
		return null;
	}

}
