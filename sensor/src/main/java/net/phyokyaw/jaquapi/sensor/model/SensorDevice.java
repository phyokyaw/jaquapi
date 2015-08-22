package net.phyokyaw.jaquapi.sensor.model;

public class SensorDevice {
	private final long id;
	private boolean on;
	private String name;
	private String description;
	private boolean errorWhenOn;
	private String onErrorMessage;

	public SensorDevice(long id) {
		this.id = id;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public boolean isErrorWhenOn() {
		return errorWhenOn;
	}

	public void setErrorWhenOn(boolean errorWhenOn) {
		this.errorWhenOn = errorWhenOn;
	}

	public String getOnErrorMessage() {
		return onErrorMessage;
	}

	public void setOnErrorMessage(String onErrorMessage) {
		this.onErrorMessage = onErrorMessage;
	}

	public boolean isOnError() {
		return (on & errorWhenOn);
	}

}
