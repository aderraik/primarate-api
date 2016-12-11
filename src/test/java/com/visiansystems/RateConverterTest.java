package com.visiansystems;

import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Ignore;
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
		return new JerseyConfig();
	}

	@Test
	@Ignore
	public void testConvertEuroToDolar() {
		Double result = target.path("/EUR/10/USD").request().get(Double.class);
		Assert.assertNotNull(result);
	}
	
}
