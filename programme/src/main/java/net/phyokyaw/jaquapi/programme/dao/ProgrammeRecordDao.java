package net.phyokyaw.jaquapi.programme.dao;

import org.springframework.data.repository.CrudRepository;

import net.phyokyaw.jaquapi.programme.model.ProgrammeRecord;

public interface ProgrammeRecordDao  extends CrudRepository<ProgrammeRecord, Long> {
	ProgrammeRecord findTopByProgrammeIdOrderByStoredTimeDesc(long programmeId);
}
