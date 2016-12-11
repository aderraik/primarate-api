package com.visiansystems;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {		
		packages(getClass().getPackage().getName());
		register(new JacksonFeature());
		//property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
	}

}
