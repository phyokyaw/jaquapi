package net.phyokyaw.jaquapi.dao.model;

import junit.framework.TestCase;
import net.phyokyaw.jaquapi.dao.model.DateTimeMode.OnOfftime;

public class DateTimeModeTest extends TestCase {

	public void testToJson() throws Exception {
		OnOfftime test = new OnOfftime(10,0,12,0);
		OnOfftime test1 = new OnOfftime(13,0,23,0);
		DateTimeMode t = new DateTimeMode(new OnOfftime[]{test, test1});
		System.out.println(t.toJson());
		//
	}

}
