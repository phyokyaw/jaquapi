package net.phyokyaw.jaquapi.core.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/core-context.xml"})
public class DateTimeModeTest extends TestCase {

	@Test
	public void testToJson() throws Exception {
		//		OnOfftime test = new OnOfftime(10,0,12,0);
		//		OnOfftime test1 = new OnOfftime(13,0,23,0);
		//		DateTimeScheduleMode t = new DateTimeScheduleMode(new OnOfftime[]{test, test1});
		//		System.out.println(t.toJson());
	}

}
