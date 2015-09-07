package net.phyokyaw.jaquapi.parameter.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.parameter.dao.ParameterDao;
import net.phyokyaw.jaquapi.parameter.model.Parameter;
import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;

@Service("parameter")
public class ParameterService {
	@Resource(name="parameters")
	private List<Parameter> parameters;

	@Autowired
	private ParameterDao dao;

	public void addParameterValue(long id, String value) {
		ParameterRecord parameterRecord = new ParameterRecord();
		parameterRecord.setParameterId(id);
		parameterRecord.setValue(value);
		dao.save(parameterRecord);
	}

	public ParameterRecord[] getParameterRecords(long id) {
		return dao.findByParameterIdOrderByStoredTimeDesc(id).toArray(new ParameterRecord[]{});
	}

	public ParameterRecord[] getLastParameterRecords(long id) {
		return dao.findTop10ByParameterIdOrderByStoredTimeDesc(id).toArray(new ParameterRecord[]{});
	}

	public ParameterRecord getLastParameterRecord(long id) {
		return dao.findTopByParameterIdOrderByStoredTimeDesc(id);
	}

	public Parameter[] getParameters() {
		for (Parameter param : parameters) {
			param.setLastRecord(getLastParameterRecord(param.getId()));
		}
		return parameters.toArray(new Parameter[]{});
	}

	public Object getParameter(long id) {
		for (Parameter param : getParameters()) {
			if (param.getId() == id) {
				return param;
			}
		}
		return null;
	}
}
