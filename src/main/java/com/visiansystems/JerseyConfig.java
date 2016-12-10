package com.visiansystems;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;

public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {		
		packages(getClass().getPackage().getName());
		register(new JacksonFeature());
		property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
	}

}
