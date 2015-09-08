package net.phyokyaw.jaquapi.ph.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import net.phyokyaw.jaquapi.ph.model.PhRecord;

public interface PhDao extends CrudRepository<PhRecord, Long> {
	@Query("Select ph FROM PhRecord ph WHERE ph.storedTime > :storedTime Order by ph.storedTime desc")
	List<PhRecord> findByDate(@Param("storedTime") Date storedTime);
	@Query("Select ph FROM PhRecord ph WHERE ph.storedTime > :storedTimeAfter AND ph.storedTime <= :storedTimeBefore Order by ph.storedTime desc")
	List<PhRecord> findByDate(@Param("storedTimeAfter") Date storedTimeAfter, @Param("storedTimeBefore") Date storedTimeBefore, Pageable pageable);
}
