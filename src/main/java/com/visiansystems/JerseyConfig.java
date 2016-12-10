package com.visiansystems;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {		
		packages(getClass().getPackage().getName());
		register(new JacksonFeature());
	}

}
