package net.phyokyaw.jaquapi;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;
import net.phyokyaw.jaquapi.wm.RandomWMMode;
import net.phyokyaw.jaquapi.wm.WaveMakerMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaveMakerControlService {
	private static final Logger logger = LoggerFactory.getLogger(WaveMakerControlService.class);

	private WaveMakerMode current;
	private WaveMakerMode previous;

	private final WaveMaker[] wm = new WaveMaker[2];

	public WaveMakerMode getRandom(double start, double end, boolean isIndi) {
		return new RandomWMMode(wm, start, end);
	}

	public WaveMakerMode getAllState(boolean isOn) {
		return null;
	}

	public WaveMakerMode getSomeState(String[] id, boolean isOn) {
		return null;
	}

	public void activate(WaveMakerMode mode) {
		activate(mode, false, 0.0d);
	}

	public void activate(WaveMakerMode mode, boolean resumePreviousMode, double afterSec) {
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
