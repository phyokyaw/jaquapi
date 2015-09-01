package net.phyokyaw.jaquapi.power.model;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.phyokyaw.jaquapi.core.model.Operatable;
import net.phyokyaw.jaquapi.core.services.AquaService;

public class I2cSwitch implements Operatable {
	private static Logger logger = LoggerFactory.getLogger(I2cSwitch.class);
	private final int id;

	public I2cSwitch(int id) {
		this.id = id;
	}

	@Override
	public void setOn(boolean isOn) throws Exception {
		String newVal = null;
		String currentHex = getHex();
		String bits = new BigInteger(currentHex, 16).toString(2);
		char bit = bits.charAt(bits.length() - id - 1);
		if ((bit == '1' && isOn) || (bit == '0' && !isOn)) {
			newVal = getHex(id, currentHex);
		}
		if (newVal != null) {
			try {
				Runtime r = Runtime.getRuntime();
				Process p;
				p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, "/usr/sbin/i2cset", "-y", "1", "0x20", "0x00", newVal});
				System.out.flush();
				p.waitFor(); // wait for process to complete
			} catch(Exception ex) {
				logger.error("Unable to execute remote command", ex);
			}
		}
	}

	public static String getHex(int id, String currentVal) {
		int val = (int) Math.pow(2, id);
		return "0x" + Integer.toHexString(~(~Integer.parseInt(currentVal, 16) ^ val));
	}

	@Override
	public boolean isOn() throws Exception {
		String currentHex = getHex();
		String bits = new BigInteger(currentHex, 16).toString(2);
		char bit = bits.charAt(bits.length() - id - 1);
		return (bit == '0');
	}

	private String getHex() throws Exception {
		String result = null;
		Runtime r = Runtime.getRuntime();
		Process p;
		String line;
		p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, "/usr/sbin/i2cget", "-y", "1", "0x20", "0x01"});
		BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		if ((line = is.readLine()) != null) {
			result = line.substring(2);
		}
		System.out.flush();
		p.waitFor(); // wait for process to complete
		if (result != null) {
			return result;
		}
		throw new Exception("Unable to get hex value from i2c command");
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}
}
