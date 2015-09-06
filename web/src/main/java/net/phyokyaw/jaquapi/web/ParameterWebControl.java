package net.phyokyaw.jaquapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.parameter.service.ParameterService;

@Controller
public class ParameterWebControl {

	@Autowired
	private ParameterService parameterService;

	@RequestMapping("/parameters")
	public @ResponseBody ModelAndView getParameters() {
		ModelAndView mav = new ModelAndView("parameters");
		mav.addObject("parameters", parameterService.getParameters());
		return mav;
	}

	@RequestMapping("/parameter_records/{id}")
	public @ResponseBody ModelAndView getParameterRecords(@PathVariable("id") long id) {
		ModelAndView mav = new ModelAndView("parameter_detail");
		mav.addObject("parameterValues", parameterService.getParameterRecords(id));
		mav.addObject("parameter", parameterService.getParameter(id));
		return mav;
	}

	@RequestMapping("/secure/add_parameter_record/")
	public String addParameter(@RequestParam("id") long id, @RequestParam("value") String value) {
		parameterService.addParameterValue(id, value);
		return "redirect:/parameters";
	}
}
