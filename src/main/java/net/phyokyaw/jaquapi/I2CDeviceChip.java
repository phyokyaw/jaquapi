package net.phyokyaw.jaquapi;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;

@Component
public class I2CDeviceChip {
	private GpioController gpio = null;
	private MCP23017GpioProvider gpioProvider = null;

	@PostConstruct
	private void setup() {
		try {
			gpio = GpioFactory.getInstance();
			gpioProvider = new MCP23017GpioProvider(I2CBus.BUS_0, 0x20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PreDestroy
	private void deactivate() {
		gpio.shutdown();
	}

	public GpioPinDigitalOutput getGpioPinDigitalOutput(Pin pin, String name) {
		return gpio.provisionDigitalOutputPin(gpioProvider, pin, name, PinState.LOW);
	}
}
