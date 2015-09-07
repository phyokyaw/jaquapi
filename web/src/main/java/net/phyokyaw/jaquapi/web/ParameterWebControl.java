package net.phyokyaw.jaquapi.web;

import java.text.SimpleDateFormat;

import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.parameter.model.ParameterRecord;
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
		ParameterRecord[] history = parameterService.getLastParameterRecords(id);
		if (history != null && history.length > 0) {
			mav.addObject("parameterHistory", new ParameterHistoryData(history));
		}
		mav.addObject("parameter", parameterService.getParameter(id));
		return mav;
	}

	@RequestMapping("/secure/add_parameter_record/")
	public String addParameter(@RequestParam("id") long id, @RequestParam("value") String value) {
		parameterService.addParameterValue(id, value);
		return "redirect:/parameters";
	}

	public static class ParameterHistoryData {
		private final String[] labels;
		private final double[] values;

		public ParameterHistoryData(ParameterRecord[] records) {
			labels = new String[records.length];
			values = new double[records.length];
			String format = labels.length > 10 ? "dd MMM" : "dd MMM HH:mm";
			for (int i = 0; i < labels.length; i++) {
				labels[i] = new SimpleDateFormat(format).format(records[i].getStoredTime());
				values[i] = Double.parseDouble(records[i].getValue());
			}
		}

		public String[] getLabels() {
			return labels;
		}
		public double[] getValues() {
			return values;
		}

		public String getLabelsFormat() {
			return JSON.toString(labels);
		}

		public String getValuesFormat() {
			return JSON.toString(values);
		}
	}
}
