package net.phyokyaw.jaquapi.parameter.dao;

import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;

import org.springframework.data.repository.CrudRepository;

public interface ParameterDao extends CrudRepository<ParameterRecord, Long> {

}
