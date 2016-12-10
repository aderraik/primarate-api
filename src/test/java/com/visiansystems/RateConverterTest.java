package com.visiansystems;

import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

public class RateConverterTest extends JerseyTest {
	
	protected WebTarget target;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		target = target("/convert");
	}
	
	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);

		return new JerseyConfig();
	}

	@Test
	public void testConvertRealToDolar() {
		Long result = target.path("/BRL/10/USD").request().get(Long.class);
		Assert.assertNotNull(result);
	}
	
}
