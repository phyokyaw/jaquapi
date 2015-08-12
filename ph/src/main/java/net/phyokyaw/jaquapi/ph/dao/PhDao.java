package net.phyokyaw.jaquapi.ph.dao;

import net.phyokyaw.jaquapi.ph.dao.model.PhRecord;

import org.springframework.data.repository.CrudRepository;

public interface PhDao extends CrudRepository<PhRecord, Long> {
}
