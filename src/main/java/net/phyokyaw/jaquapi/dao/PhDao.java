package net.phyokyaw.jaquapi.dao;

import net.phyokyaw.jaquapi.dao.model.PhRecord;

import org.springframework.data.repository.CrudRepository;

public interface PhDao extends CrudRepository<PhRecord, Long> {
}
