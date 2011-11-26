package com.guigrasoft.remote.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.guigrasoft.remote.spring.root.ApplicationConfig;
import com.guigrasoft.remote.spring.root.PersistenceConfig;
import com.guigrasoft.remote.spring.root.SecurityConfig;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceConfig.class, SecurityConfig.class },loader = AnnotationConfigContextLoader.class )
public final class SpringTest{

	@Autowired
	RESTPaths paths;

	// API

	public final String getRootURL() {
		return this.paths.getContext() + "/api/admin";
	}

	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		//
		System.out.println(this.paths.getContext());

		System.out.println(this.getRootURL());
	}

}
