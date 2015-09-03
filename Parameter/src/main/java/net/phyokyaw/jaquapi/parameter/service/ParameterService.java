package net.phyokyaw.jaquapi.parameter.service;


import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("parameter")
public class ParameterService {
	
	
	
	@Autowired
	private ParameterRecord dao;
	public void addParameterValue(long id, String value) {
		ParameterRecord parameterRecord = new ParameterRecord();
		parameterRecord.set
	}
}
