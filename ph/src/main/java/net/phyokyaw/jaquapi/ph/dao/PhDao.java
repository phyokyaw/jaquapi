package net.phyokyaw.jaquapi.ph.dao;

import org.springframework.data.repository.CrudRepository;

import net.phyokyaw.jaquapi.ph.model.PhRecord;

public interface PhDao extends CrudRepository<PhRecord, Long> {
}
