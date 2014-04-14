package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.controls.WaveMakerControlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class WmWebControl {

	@Autowired
	@Qualifier("wm")
	private WaveMakerControlService wmService;

	@RequestMapping("/wmState")
	public @ResponseBody boolean getState() {
		return true;
	}

	@RequestMapping("/wmActivateRandomMode")
	public void activateRandomMode(@RequestParam("min_on") int min_on, @RequestParam("max_on") int max_on, @RequestParam("isSync") boolean isSync) {
		//	WaveMakerMode newMode = wmService.getRandomMode(min_on, max_on, isSync);
		//		wmService.activate(newMode);
	}

	@RequestMapping("/wmActivateAllMode")
	public void activateAllMode(@RequestParam("isOn") boolean isOn) {
		//WaveMakerMode newMode = wmService.getAllMode(isOn);
		//wmService.activate(newMode);
	}

	@RequestMapping("/wmActivateSomeMode")
	public void activateSomeMode(@RequestParam("names[]") String[] names) {
		//WaveMakerMode newMode = wmService.getSomeOnMode(names);
		//wmService.activate(newMode);
	}
}
