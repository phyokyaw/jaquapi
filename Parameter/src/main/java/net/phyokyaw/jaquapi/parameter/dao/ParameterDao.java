package net.phyokyaw.jaquapi.parameter.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;

public interface ParameterDao extends CrudRepository<ParameterRecord, Long> {
	ParameterRecord findTopByParameterIdOrderByStoredTimeDesc(long parameterId);
	List<ParameterRecord> findByParameterIdOrderByStoredTimeDesc(long parameterId);
	List<ParameterRecord> findTop10ByParameterIdOrderByStoredTimeDesc(long parameterId);
}
