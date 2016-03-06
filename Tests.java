package hillbillies.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests {
	
	private static Unit unit1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		unit1 = new Unit("Joris",new int[] {5,12,20}, 90, 85, 80, 75, false);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertEquals("Jori",unit1.getName());
		
	}

}
