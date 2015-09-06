package net.phyokyaw.jaquapi.parameter.model;

public class Parameter {
	private final int id;
	private String name;
	private String unit;
	private String shortName;
	private ParameterRecord lastRecord;

	public Parameter(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public ParameterRecord getLastRecord() {
		return lastRecord;
	}

	public void setLastRecord(ParameterRecord lastRecord) {
		this.lastRecord = lastRecord;
	}
}
