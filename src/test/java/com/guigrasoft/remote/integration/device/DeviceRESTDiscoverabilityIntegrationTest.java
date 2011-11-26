package com.guigrasoft.remote.integration.device;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.guigrasoft.remote.common.util.HttpConstants;
import com.guigrasoft.remote.common.util.RESTURIUtil;
import com.guigrasoft.remote.device.model.Device;
import com.guigrasoft.remote.integration.DeviceRESTTemplate;
import com.guigrasoft.remote.integration.ExamplePaths;
import com.guigrasoft.remote.integration.http.HTTPLinkHeaderUtils;
import com.guigrasoft.remote.integration.security.SecurityComponent;
import com.guigrasoft.remote.spring.root.ApplicationConfig;
import com.guigrasoft.remote.spring.root.PersistenceConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class },loader = AnnotationConfigContextLoader.class )
public class DeviceRESTDiscoverabilityIntegrationTest{
	@Autowired
	ExamplePaths paths;

	@Autowired
	DeviceRESTTemplate restTemplate;

	@Autowired
	SecurityComponent securityComponent;

	// tests

	// GET

	@Test
	public final void whenResourceIsRetrieved_thenURIToCreateANewResourceIsDiscoverable(){
		// Given
		final String uriOfNewlyCreatedResource = this.restTemplate.createResource();

		// When
		final Response response = this.givenAuthenticated().get( uriOfNewlyCreatedResource );

		// Then
		final String linkHeader = response.getHeader( "Link" );
		final String uriForResourceCreation = HTTPLinkHeaderUtils.extractSingleURI( linkHeader );
		final Response secondCreationResponse = this.givenAuthenticated()
				.contentType(HttpConstants.MIME_JSON)
				.body(new Device(randomAlphabetic(6)))
				.post(uriForResourceCreation);
		assertThat( secondCreationResponse.getStatusCode(), is( 201 ) );
	}

	@Test
	public final void whenResourceIsRetrieved_thenURIToGetAllResourcesIsDiscoverable(){
		// Given
		final String uriOfExistingResource = this.restTemplate.createResource();

		// When
		final Response getResponse = this.givenAuthenticated().get( uriOfExistingResource );

		// Then
		final String uriToAllResources = HTTPLinkHeaderUtils.extractURIByRel( getResponse.getHeader( "Link" ), RESTURIUtil.REL_COLLECTION );

		final Response getAllResponse = this.givenAuthenticated().get( uriToAllResources );
		assertThat( getAllResponse.getStatusCode(), is( 200 ) );
	}

	@Test
	public final void whenGetIsDoneOnRoot_thenSomeURIAreDiscoverable(){
		// When
		final Response getOnRootResponse = this.givenAuthenticated().get( this.paths.getRootURL() );

		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );

		assertThat( allURIsDiscoverableFromRoot, not( Matchers.<String> empty() ) );
	}
	@Test
	public final void whenGetIsDoneOnRoot_thenDeviceURIIsDiscoverable() {
		// When
		final Response getOnRootResponse = this.givenAuthenticated().get( this.paths.getRootURL() );

		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );
		final int indexOfDeviceUri = Iterables.indexOf(
				allURIsDiscoverableFromRoot,
				Predicates.containsPattern(this.paths.getDeviceURL()));
		assertThat(indexOfDeviceUri, greaterThanOrEqualTo(0));
	}

	// GET (all)

	// POST

	@SuppressWarnings( "unchecked" )
	@Test
	public final void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions(){
		// Given
		final String uriOfExistingResource = this.restTemplate.createResource();

		// When
		final Response res = this.givenAuthenticated().post( uriOfExistingResource );

		// Then
		final String allowHeader = res.getHeader( HttpHeaders.ALLOW );
		assertThat( allowHeader, AnyOf.<String> anyOf( containsString( "GET" ), containsString( "PUT" ), containsString( "DELETE" ) ) );
	}

	@Test
	public final void whenResourceIsCreated_thenURIOfTheNewlyCreatedResourceIsDiscoverable(){
		// When
		final Device unpersistedResource = new Device(randomAlphabetic(6));
		final Response createResp = this.givenAuthenticated()
				.contentType(HttpConstants.MIME_JSON).body(unpersistedResource)
				.post(this.paths.getDeviceURL());
		final String uriOfNewlyCreatedResource = createResp.getHeader( HttpHeaders.LOCATION );

		// Then
		final Response response = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriOfNewlyCreatedResource );

		final Device resourceFromServer = response.body().as(Device.class);
		assertThat( unpersistedResource, equalTo( resourceFromServer ) );
	}

	// PUT

	// DELETE

	// util

	private final RequestSpecification givenAuthenticated(){
		return this.securityComponent.givenAuthenticatedByBasicAuth();
	}

}
