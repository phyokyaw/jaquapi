package net.phyokyaw.jaquapi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
	ScheduledExecutorService scheduledExecutorService =
			Executors.newScheduledThreadPool(5);

	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<TemperatureService> ref = context.getServiceReference(TemperatureService.class);
		final TemperatureService service = context.getService(ref);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				service.update();
			}
		}, 0L, 1L, TimeUnit.MINUTES);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				service.record();
			}
		}, 0L, 5L, TimeUnit.MINUTES);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		scheduledExecutorService.shutdown();
	}
}
