package net.phyokyaw.jaquapi.programme.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProgrammeRecord {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Long programmeId;

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

	public Long getProgrammeId() {
		return programmeId;
	}

	public void setProgrammeId(Long programmeId) {
		this.programmeId = programmeId;
	}
}
