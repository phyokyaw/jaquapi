package net.phyokyaw.jaquapi.ph.dao;

import java.util.Date;
import java.util.List;

import net.phyokyaw.jaquapi.ph.model.PhRecord;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PhDao extends CrudRepository<PhRecord, Long> {
	@Query("Select ph FROM PhRecord ph WHERE ph.storedTime > :storedTime Order by ph.storedTime desc")
	List<PhRecord> findByDate(@Param("storedTime") Date storedTime);
}
