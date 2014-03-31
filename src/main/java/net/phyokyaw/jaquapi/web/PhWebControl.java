package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.PhService;
import net.phyokyaw.jaquapi.dao.model.PhRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PhWebControl {

	@Autowired
	@Qualifier("ph")
	private PhService phService;

	@RequestMapping("/ph")
	public @ResponseBody PhRecord getPh() {
		return phService.getPhRecord();
	}}
