package net.phyokyaw.jaquapi.dao.model;

import java.util.Date;

import javax.persistence.Basic;
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

	@Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date storedTime;


    public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
