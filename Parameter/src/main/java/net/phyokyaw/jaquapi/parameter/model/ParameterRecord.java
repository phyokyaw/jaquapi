package net.phyokyaw.jaquapi.parameter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import net.phyokyaw.jaquapi.core.DateTimeUtil;

@Entity
public class ParameterRecord {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Long parameterId;

	@Column
	private String value;

	// columnDefinition could simply be = "TIMESTAMP", as the other settings are the MySQL default
	@Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date storedTime;

	public Date getStoredTime() {
		return storedTime;
	}

	public void setStoredTime(Date storedTime) {
		this.storedTime = storedTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getParameterId() {
		return parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}

	public String timeFromNow() {
		return DateTimeUtil.getTimeDifference(DateTime.now().toDate(), getStoredTime());
	}
}
