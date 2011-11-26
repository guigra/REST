package com.guigrasoft.remote.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.guigrasoft.remote.common.util.HttpConstants;
import com.guigrasoft.remote.common.util.RESTURIUtil;

@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener< SingleResourceRetrieved >{
	
	@Override
	public final void onApplicationEvent( final SingleResourceRetrieved resourceRetrievedEvent ){
		Preconditions.checkNotNull( resourceRetrievedEvent );
		
		final HttpServletRequest request = resourceRetrievedEvent.getRequest();
		final HttpServletResponse response = resourceRetrievedEvent.getResponse();
		
		this.addLinkHeaderOnSingleResourceRetrieval( request, response );
	}
	
	final void addLinkHeaderOnSingleResourceRetrieval( final HttpServletRequest request, final HttpServletResponse response ){
		final StringBuffer requestURL = request.getRequestURL();
		final int positionOfLastSlash = requestURL.lastIndexOf( "/" );
		final String uriForEntityCreation = requestURL.substring( 0, positionOfLastSlash );
		
		final String linkHeaderValue = RESTURIUtil.createLinkHeader( uriForEntityCreation, RESTURIUtil.REL_COLLECTION );
		response.addHeader( HttpConstants.LINK_HEADER, linkHeaderValue );
	}
	
}
