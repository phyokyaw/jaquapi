package net.phyokyaw.jaquapi.temperature;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/temperature-context.xml"})
public class TemperatureServiceTest
extends TestCase
{
	@Autowired
	TemperatureService service;

	@Test
	public void testApp()
	{
		assertNotNull(service);
		assertNotNull(service.getTemperature());
	}
}
