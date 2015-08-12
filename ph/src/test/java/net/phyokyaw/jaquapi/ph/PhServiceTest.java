package net.phyokyaw.jaquapi.ph;

import static org.junit.Assert.*;
import net.phyokyaw.jaquapi.ph.services.PhService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/ph-context.xml"})
public class PhServiceTest {
	@Autowired
	@Qualifier("ph")
	private PhService phService;
	
	@Test
	public void test() {
		assertTrue(phService.getValue() == 0.0d);
	}
}
