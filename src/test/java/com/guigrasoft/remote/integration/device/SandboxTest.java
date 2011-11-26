package com.guigrasoft.remote.integration.device;

import static com.jayway.restassured.RestAssured.get;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.guigrasoft.remote.spring.root.ApplicationConfig;
import com.guigrasoft.remote.spring.root.PersistenceConfig;
import com.jayway.restassured.response.Response;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public class SandboxTest{
	
	// GET
	@SuppressWarnings( "unused" )
	@Test
	public final void when_then(){
		// Given
		
		// When
		final Response response = get( "https://api.github.com/users/steveklabnik/gists" );
		
		// Then
		final Map< String, String > headers = response.getHeaders();
		final String header = response.getHeader( "Link" );
	}
	
}
