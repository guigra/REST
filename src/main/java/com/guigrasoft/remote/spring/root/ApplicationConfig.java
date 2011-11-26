package com.guigrasoft.remote.spring.root;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = "com.guigrasoft.remote", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
// @PropertySource( { "classpath:persistence.properties", "classpath:restfull.properties" } )
public class ApplicationConfig{

	// API

	@Bean
	public static PropertyPlaceholderConfigurer properties(){
		final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		final Resource[] resources = new ClassPathResource[ ] { new ClassPathResource( "persistence.properties" ), new ClassPathResource( "restfull.properties" ) };
		ppc.setLocations( resources );
		ppc.setIgnoreUnresolvablePlaceholders( true );
		return ppc;
	}

}
