package net.phyokyaw.jaquapi.wm;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class RandomWMMode extends WaveMakerMode {
	private final double min_on;
	private final double max_on;
	private final boolean isSync;
	private int runningIndex;
	private ScheduledFuture<?> runningSchedule;
	private boolean activated = false;
	public RandomWMMode(WaveMaker[] wm, double min_on, double max_on, boolean isSync) {
		super(wm);
		this.min_on = min_on;
		this.max_on = max_on;
		this.isSync = isSync;
	}

	@Override
	public void deactivate() {
		if (activated) {
			if (runningSchedule != null) {
				runningSchedule.cancel(false);
			}
			for (WaveMaker m : availableWMs) {
				m.deactivateAndStop();
			}
		}
		activated = false;
	}

	@Override
	public void activate() {
		if (isSync) {
			availableWMs[runningIndex].activate(min_on, max_on);
			final int size = availableWMs.length;
			runningSchedule = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					synchronized (availableWMs) {
						if (availableWMs[runningIndex].isFinished()) {
							runningIndex++;
							if (size == runningIndex) {
								runningIndex = 0;
							}
							availableWMs[runningIndex].activate(min_on, max_on);
						}
					}
				}
			}, 0L, 300, TimeUnit.MILLISECONDS);
		} else {
			for (WaveMaker m : availableWMs) {
				m.activate(min_on, max_on);
			}
		}
		activated = true;
	}

}
