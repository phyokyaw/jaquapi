package net.phyokyaw.jaquapi.controls;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.I2CDeviceChip;
import net.phyokyaw.jaquapi.dao.model.WaveMaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@Service("wm")
public class WaveMakerControlService {
	private static final Logger logger = LoggerFactory.getLogger(WaveMakerControlService.class);

	//	private WaveMakerMode current;
	//	private WaveMakerMode previous;

	@Autowired
	private I2CDeviceChip i2cChip;

	private final WaveMaker[] wm = new WaveMaker[2];

	@PostConstruct
	private void setup() {
		//		wm[0] = new WaveMaker("Left");
		//		wm[0].setDevice(i2cChip.getGpioPinDigitalOutput(MCP23017Pin.GPIO_B0.getName()));
		//		//		logger.info("Wave maker devices created with name " + wm[0] + " with " + wm[0].isOn());
		//		wm[1] = new WaveMaker("Right");
		//		wm[1].setDevice(i2cChip.getGpioPinDigitalOutput(MCP23017Pin.GPIO_B1.getName()));
		//		//		logger.info("Wave maker devices created with name " + wm[1] + " with " + wm[1].isOn());
	}

	//	public WaveMakerMode getRandomMode(double start, double end, boolean isSync) {
	//		//		return new RandomWMMode(wm, start, end, isSync);
	//	}

	//	public WaveMakerMode getAllMode(boolean isOn) {
	//		return new AllSameWMMode(wm, isOn);
	//	}
	//
	//	public WaveMakerMode getSomeOnMode(String[] names) {
	//		return new SomeOnWMMode(wm, names);
	//	}
	//
	//	public void activate(WaveMakerMode mode) {
	//		activate(mode, false, 0.0d);
	//	}
	//
	//	public void activate(WaveMakerMode mode, boolean resumePreviousMode, double afterSec) {
	//		logger.info("Activating mode");
	//		if (current != null) {
	//			current.deactivate();
	//			previous = current;
	//		}
	//		current = mode;
	//		current.activate();
	//		if (resumePreviousMode) {
	//			// TODO
	//		}
	//	}

}
