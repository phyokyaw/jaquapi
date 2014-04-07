package net.phyokyaw.jaquapi.dao;

import java.util.List;

import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureDao extends CrudRepository<TemperatureRecord, Long> {
	@Query("Select tr from TemperatureRecord tr ORDER BY tr.storedTime desc")
	List<TemperatureRecord> findLatest();
}
