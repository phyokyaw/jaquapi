package net.phyokyaw.jaquapi.temperature.dao;

import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TemperatureDao extends CrudRepository<TemperatureRecord, Long> {
	@Query("Select tr FROM TemperatureRecord tr WHERE tr.storedTime > :storedTime Order by tr.storedTime desc")
	List<TemperatureRecord> findByDate(@Param("storedTime") Date storedTime);
	@Query("Select tr FROM TemperatureRecord tr WHERE tr.storedTime > :storedTimeAfter AND tr.storedTime <= :storedTimeBefore Order by tr.storedTime desc")
	List<TemperatureRecord> findByDate(@Param("storedTimeAfter") Date storedTimeAfter, @Param("storedTimeBefore") Date storedTimeBefore);
}
