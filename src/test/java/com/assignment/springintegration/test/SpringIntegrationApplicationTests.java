package com.assignment.springintegration.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignment.springintegration.SpringIntegrationApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/ApplicationContext.xml"})
public class SpringIntegrationApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	 @Test
     public void testJsonToCsvContext() {
 //    Assert.assertNotNull(applicationContext.getBean("findHandler"));
     }

}
