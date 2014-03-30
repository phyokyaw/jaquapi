package net.phyokyaw.jaquapi.dao;

import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.springframework.data.repository.CrudRepository;

public interface TemperatureDao extends CrudRepository<TemperatureRecord, Long> {
}
