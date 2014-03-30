package com.sjl.dao;

import org.springframework.data.repository.CrudRepository;

import com.sjl.dao.model.TemperatureRecord;

public interface TemperatureDao extends CrudRepository<TemperatureRecord, Long> {
}
