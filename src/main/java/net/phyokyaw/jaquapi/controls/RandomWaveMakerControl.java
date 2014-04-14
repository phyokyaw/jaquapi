package net.phyokyaw.jaquapi.controls;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class RandomWaveMakerControl extends DeviceControl {
	private final double min_on;
	private final double max_on;
	private final boolean isSync;

	private int runningIndex;
	private ScheduledFuture<?> runningSchedule;
	private boolean activated = false;

	public RandomWaveMakerControl(WaveMaker[] waveMakers, double min_on, double max_on, boolean isSync) {
		super(waveMakers);
		this.min_on = min_on;
		this.max_on = max_on;
		this.isSync = isSync;
	}

	@Override
	public void deactivate() throws InterruptedException {
		super.deactivate();
		if (activated) {
			for (Device device : devices) {
				((WaveMaker) device).deactivate();
			}
			if (runningSchedule != null) {
				runningSchedule.cancel(false);
			}
		}
		activated = false;
	}

	@Override
	public void activate() throws Exception {
		super.activate();
		if (isSync) {
			((WaveMaker) devices[runningIndex]).activate(min_on, max_on);
			final int size = devices.length;
			runningSchedule = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					if (((WaveMaker) devices[runningIndex]).isReady()) {
						runningIndex++;
						if (size == runningIndex) {
							runningIndex = 0;
						}
						if (!Thread.interrupted()) {
							((WaveMaker) devices[runningIndex]).activate(min_on, max_on);
						}
					}

				}
			}, 0L, 300, TimeUnit.MILLISECONDS);
		} else {
			for (Device device : devices) {
				((WaveMaker) device).activate(min_on, max_on);
			}
		}
		activated = true;
	}
}
