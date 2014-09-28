package net.phyokyaw.jaquapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;

//@Component
public class I2CDeviceChip {
	private static final Logger logger = LoggerFactory.getLogger(I2CDeviceChip.class);

	private GpioController gpio = null;
	private MCP23017GpioProvider gpioProvider = null;
	private final Map<String, GpioPinDigitalOutput> outputPins = new HashMap<String, GpioPinDigitalOutput>();

	@PostConstruct
	private void setup() {
		logger.debug("Setting up I2CDeviceChip");
		try {
			gpio = GpioFactory.getInstance();
			gpioProvider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);
			logger.info("gpio provider created");
			outputPins.put(MCP23017Pin.GPIO_B0.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B0, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B1.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B1, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B2.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B2, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B3.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B3, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B4.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B4, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B5.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B5, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B6.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B6, PinState.HIGH));
			outputPins.put(MCP23017Pin.GPIO_B7.getName(), gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_B7, PinState.HIGH));
			logger.info("Outpins provisioned");
		} catch (IOException e) {
			logger.error("Unable to create GPIO provider", e);
		}
	}

	@PreDestroy
	private void deactivate() {
		gpio.shutdown();
	}

	public GpioPinDigitalOutput getGpioPinDigitalOutput(String pinName) {
		return outputPins.get(pinName);
	}
}
