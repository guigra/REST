package com.guigrasoft.remote.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guigrasoft.remote.test.RESTPaths;

@Component
public final class ExamplePaths{

	@Autowired
	RESTPaths paths;

	// API

	public final String getRootURL(){
		return this.paths.getContext() + "/api/admin";
	}

	public final String getDeviceURL() {
		return this.getRootURL() + "/device";
	}

	public final String getLoginURL(){
		return this.paths.getContext() + "/j_spring_security_check";
	}

}
