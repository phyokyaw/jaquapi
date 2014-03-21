package net.phyokyaw.jaquapi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	ScheduledExecutorService scheduledExecutorService =
			Executors.newScheduledThreadPool(5);

	@Override
	public void start(BundleContext context) throws Exception {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//
			}
		}, 0L, 1L, TimeUnit.MINUTES);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		scheduledExecutorService.shutdown();
	}
}
