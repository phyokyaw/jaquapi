package net.phyokyaw.jaquapi.power.model;


import net.phyokyaw.jaquapi.core.model.Operatable;

import org.springframework.beans.factory.annotation.Autowired;

import remote.ControllerDataService;
import remote.ValueUpdateListener;

public class I2cSwitch implements Operatable, ValueUpdateListener {
	
// 	private static Logger logger = LoggerFactory.getLogger(I2cSwitch.class);
	private final int id;
	private String 
	
	@Autowired
	private ControllerDataService controllerDataService;

	public I2cSwitch(int id) {
		this.id = id;
	}

	@Override
	public void setOn(boolean on) throws Exception {
//		String newVal = null;
//		String currentHex = getHex();
//		String bits = new BigInteger(currentHex, 16).toString(2);
//		char bit = bits.charAt(bits.length() - id - 1);
//		if ((bit == '1' && isOn) || (bit == '0' && !isOn)) {
//			newVal = getHex(id, currentHex);
//		}
//		if (newVal != null) {
//			try {
//				Runtime r = Runtime.getRuntime();
//				Process p;
//				p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, "/usr/sbin/i2cset", "-y", "1", "0x20", "0x00", newVal});
//				System.out.flush();
//				p.waitFor(); // wait for process to complete
//			} catch(Exception ex) {
//				logger.error("Unable to execute remote command", ex);
//			}
//		}
		if (this.on != on) {
			setValue(controllerDataService.setDeviceUpdate(id, on));
		}
//		if (controllerDataService.getDeviceStatus(id) != isOn) {
//			controllerDataService.setDeviceUpdate(id, isOn);
//		}
	}

//	public static String getHex(int id, String currentVal) {
//		int val = (int) Math.pow(2, id);
//		return "0x" + Integer.toHexString(~(~Integer.parseInt(currentVal, 16) ^ val));
//	}

	@Override
	public boolean isOn() throws Exception {
//		String currentHex = getHex();
//		String bits = new BigInteger(currentHex, 16).toString(2);
//		char bit = bits.charAt(bits.length() - id - 1);
//		return (bit == '0');
		return on;
	}

//	private String getHex() throws Exception {
//		String result = null;
//		Runtime r = Runtime.getRuntime();
//		Process p;
//		String line;
//		p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, "/usr/sbin/i2cget", "-y", "1", "0x20", "0x00"});
//		BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
//		if ((line = is.readLine()) != null) {
//			result = line.substring(2);
//		}
//		System.out.flush();
//		p.waitFor(); // wait for process to complete
//		if (result != null) {
//			return result;
//		}
//		throw new Exception("Unable to get hex value from i2c command");
//	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}

	@Override
	public void setValue(String value) {
		on = true;
	}
}
