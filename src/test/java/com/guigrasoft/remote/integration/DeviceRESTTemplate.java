package com.guigrasoft.remote.integration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.guigrasoft.remote.common.util.HttpConstants;
import com.guigrasoft.remote.device.model.Device;
import com.guigrasoft.remote.integration.security.SecurityComponent;
import com.jayway.restassured.response.Response;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class DeviceRESTTemplate{

	@Autowired
	ExamplePaths paths;

	@Autowired
	SecurityComponent securityComponent;

	public DeviceRESTTemplate(){
		super();
	}

	//
	public final String createResource(){
		return this.createResource(new Device(randomAlphabetic(6)));
	}

	public final String createResource(final Device resource) {
		Preconditions.checkNotNull( resource );

		final Response response = this.securityComponent
				.givenAuthenticatedByBasicAuth()
				.contentType(HttpConstants.MIME_JSON).body(resource)
				.post(this.paths.getDeviceURL());

		return response.getHeader( HttpHeaders.LOCATION );
	}

	public final String getResourceAsJson( final String uriOfResource ){
		return this.securityComponent.givenAuthenticatedByBasicAuth().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriOfResource ).asString();
	}

}
