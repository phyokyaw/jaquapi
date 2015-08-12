package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.ph.dao.model.PhRecord;
import net.phyokyaw.jaquapi.ph.services.PhService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class PhWebControl {

	@Autowired
	@Qualifier("ph")
	private PhService phService;

	@RequestMapping("/ph")
	public @ResponseBody PhRecord getPh() {
		return phService.getPhRecord();
	}


}
