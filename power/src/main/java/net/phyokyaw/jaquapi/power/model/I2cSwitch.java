package net.phyokyaw.jaquapi.power.model;


import java.math.BigInteger;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.core.model.Operatable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I2cSwitch implements Operatable {

	private static Logger logger = LoggerFactory.getLogger(I2cSwitch.class);

	private final int id;
	private String currentHex;

//	@Autowired
//	private ControllerDataService controllerDataService;

	public I2cSwitch(int id) {
		this.id = id;
	}

	@PostConstruct
	private void setup() {
//		controllerDataService.addValueUpdateListener("devices", this);
	}

	@Override
	public void setOn(boolean on) throws Exception {
		if (isOn() != on) {
			//controllerDataService.setI2cUpdate(getNewHex(id, currentHex));
		}
	}

	public static String getNewHex(int id, String currentVal) {
		int val = (int) Math.pow(2, id);
		String newHex = "0x" + Integer.toHexString(~(~Integer.parseInt(currentVal, 16) ^ val));
		logger.debug("New hex " + newHex + " for id " + id);
		return newHex;
	}

	@Override
	public boolean isOn() throws Exception {
		String bits = String.format("%8s", new BigInteger(currentHex, 16).toString(2)).replace(' ', '0');
		char bit = bits.charAt(bits.length() - id - 1);
		return (bit == '0');
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}


	@Override
	public boolean isReady() {
		return currentHex != null;
	}
}
