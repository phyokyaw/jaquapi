package net.phyokyaw.jaquapi.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TemperatureRecord {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private double value;

	// columnDefinition could simply be = "TIMESTAMP", as the other settings are the MySQL default
	@Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date storedTime;


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
