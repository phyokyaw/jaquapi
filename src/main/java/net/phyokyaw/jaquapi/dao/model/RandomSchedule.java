package net.phyokyaw.jaquapi.dao.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RandomSchedule extends Schedule {
	private final double min_on;
	private final double max_on;
	private final boolean isSync;

	private enum State {
		RUNNING, RESTING, READY
	}

	private int runningIndex;
	private ScheduledFuture<?> runningSchedule;
	private boolean activated = false;
	private final Map<Device, State> state = new ConcurrentHashMap<Device, State>();

	public RandomSchedule(double min_on, double max_on, boolean isSync) {
		this.min_on = min_on;
		this.max_on = max_on;
		this.isSync = isSync;
	}

	@Override
	public void addDevice(Device device) {
		super.addDevice(device);
		state.put(device, State.READY);
	}

	@Override
	public void deactivate() {
		if (activated) {
			if (runningSchedule != null) {
				runningSchedule.cancel(false);
			}
			for (Device wm : devices) {
				deactivateAndStop(wm);
			}
		}
		activated = false;
	}

	@Override
	public void activate() {
		if (isSync) {
			activate(devices.get(runningIndex), min_on, max_on);
			final int size = devices.size();
			runningSchedule = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {

					if (availableWMs[runningIndex].isFinished()) {
						runningIndex++;
						if (size == runningIndex) {
							runningIndex = 0;
						}
						availableWMs[runningIndex].activate(min_on, max_on);
					}

				}
			}, 0L, 300, TimeUnit.MILLISECONDS);
		} else {
			for (Device m : devices) {
				activate(m, min_on, max_on);
			}
		}
		activated = true;
	}

	private void activate(Device device, double min_on2, double max_on2) {
		// TODO Auto-generated method stub

	}

	private void deactivateAndStop(Device wm) {
		// TODO Auto-generated method stub

	}
}
