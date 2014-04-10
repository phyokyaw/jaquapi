package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.WaveMakerControlService;
import net.phyokyaw.jaquapi.wm.WaveMakerMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WmWebControl {

	@Autowired
	@Qualifier("wm")
	private WaveMakerControlService wmService;

	@RequestMapping("/wmState")
	public @ResponseBody boolean getState() {
		return true;
	}

	@RequestMapping("/wmActivateRandomMode")
	public void activateRandomMode(@RequestParam("start") int start, @RequestParam("start") int end, @RequestParam("isSync") boolean isSync) {
		WaveMakerMode newMode = wmService.getRandom(start, end, isSync);
		wmService.activate(newMode);
	}

	@RequestMapping("/wmActivateAllMode")
	public void activateAllMode(@RequestParam("isOn") boolean isOn) {
		WaveMakerMode newMode = wmService.getAllState(isOn);
		wmService.activate(newMode);
	}

	@RequestMapping("/wmActivateSomeMode")
	public void activateSomeMode(@RequestParam("names[]") String[] names, @RequestParam("isOn") boolean isOn) {
		WaveMakerMode newMode = wmService.getSomeState(names, isOn);
		wmService.activate(newMode);
	}
}
