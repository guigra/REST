package com.guigrasoft.remote.discovery;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import com.guigrasoft.remote.common.util.HttpConstants;
import com.guigrasoft.remote.common.util.RESTURIUtil;

/**
 * @author eugenp
 */
@Controller
final class DiscoveryController{

	// API

	@RequestMapping( value = "admin",method = RequestMethod.GET )
	@ResponseStatus( value = HttpStatus.NO_CONTENT )
	public final void adminRoot( final HttpServletRequest request, final HttpServletResponse response ){
		final String rootUri = request.getRequestURL().toString();

		final URI deviceUri = new UriTemplate("{rootUri}/{resource}").expand(
				rootUri, "device");
		final String linkToDevice = RESTURIUtil.createLinkHeader(
				deviceUri.toASCIIString(), RESTURIUtil.REL_COLLECTION);
		final String linkToTest = RESTURIUtil.createLinkHeader( "test", RESTURIUtil.REL_COLLECTION );

		final String headerLink = RESTURIUtil.gatherLinkHeaders(linkToDevice,
				linkToTest);
		System.out.println("Link: " + headerLink);
		response.addHeader(HttpConstants.LINK_HEADER, headerLink);
	}

}
