package net.phyokyaw.jaquapi;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;
import net.phyokyaw.jaquapi.wm.AllSameWMMode;
import net.phyokyaw.jaquapi.wm.RandomWMMode;
import net.phyokyaw.jaquapi.wm.SomeOnWMMode;
import net.phyokyaw.jaquapi.wm.WaveMakerMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi4j.gpio.extension.mcp.MCP23017Pin;

@Service
public class WaveMakerControlService {
	private static final Logger logger = LoggerFactory.getLogger(WaveMakerControlService.class);

	private WaveMakerMode current;
	private WaveMakerMode previous;

	@Autowired
	private I2CDeviceChip i2cChip;

	private final WaveMaker[] wm = new WaveMaker[2];

	@PostConstruct
	private void setup() {
		wm[0] = new WaveMaker(i2cChip.getGpioPinDigitalOutput(MCP23017Pin.GPIO_B0, "Left WM"));
		logger.info("Wave maker devices created with name " + wm[0] + " with " + wm[0].isOn());
		wm[1] = new WaveMaker(i2cChip.getGpioPinDigitalOutput(MCP23017Pin.GPIO_B1, "Right WM"));
		logger.info("Wave maker devices created with name " + wm[1] + " with " + wm[1].isOn());
	}

	public WaveMakerMode getRandom(double start, double end, boolean isIndi) {
		return new RandomWMMode(wm, start, end);
	}

	public WaveMakerMode getAllState(boolean isOn) {
		return new AllSameWMMode(wm, isOn);
	}

	public WaveMakerMode getSomeState(String[] names, boolean isOn) {
		return new SomeOnWMMode(wm, names, isOn);
	}

	public void activate(WaveMakerMode mode) {
		activate(mode, false, 0.0d);
	}

	public void activate(WaveMakerMode mode, boolean resumePreviousMode, double afterSec) {
		logger.info("Activating mode");
		if (current != null) {
			current.deactivate();
			previous = current;
		}
		current = mode;
		current.activate();
		if (resumePreviousMode) {
			// TODO
		}
	}
}
