package net.phyokyaw.power.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.phyokyaw.jaquapi.power.services.PowerControlDeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/power-context.xml"})
public class PowerControlDeviceServiceTest {

	@Autowired
	private PowerControlDeviceService powerControlDeviceService;
	@Test
	public void test() {
		assertNotNull(powerControlDeviceService);
	}

}
