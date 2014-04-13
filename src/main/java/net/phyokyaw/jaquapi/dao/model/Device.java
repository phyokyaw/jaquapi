package net.phyokyaw.jaquapi.dao.model;

public class Device implements Schedulable {
	private final String name;

	public Device(String name) {
		this.name = name;
	}

	@Override
	public void setSchedule(Schedule schedule) {
		schedule.addDevice(this);
	}

	public String getName() {
		return name;
	}

}
