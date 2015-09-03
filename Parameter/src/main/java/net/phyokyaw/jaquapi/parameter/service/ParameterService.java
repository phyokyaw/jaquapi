package net.phyokyaw.jaquapi.parameter.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;

@Service("parameter")
public class ParameterService {



	@Autowired
	private ParameterRecord dao;
	public void addParameterValue(long id, String value) {
		ParameterRecord parameterRecord = new ParameterRecord();
	}
}
