package net.phyokyaw.jaquapi.dao;

import java.util.Date;
import java.util.List;

import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TemperatureDao extends CrudRepository<TemperatureRecord, Long> {
	@Query("Select tr FROM TemperatureRecord tr WHERE tr.storedTime > :storedTime")
	List<TemperatureRecord> findByDate(@Param("storedTime") Date storedTime);
}
