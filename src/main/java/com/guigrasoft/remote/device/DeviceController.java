package com.guigrasoft.remote.device;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.guigrasoft.remote.common.event.ResourceCreated;
import com.guigrasoft.remote.common.event.SingleResourceRetrieved;
import com.guigrasoft.remote.common.util.RestPreconditions;
import com.guigrasoft.remote.device.model.Device;

@Controller
final class DeviceController {

	@Autowired
	DeviceService service;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	public DeviceController() {
		super();
	}

	// API

	@RequestMapping(value = "admin/device", method = RequestMethod.GET)
	@ResponseBody
	public final List<Device> getAll() {
		return this.service.getAll();
	}

	@RequestMapping(value = "admin/device/{id}", method = RequestMethod.GET)
	@ResponseBody
	public final Device get(@PathVariable("id") final Long id,
			final HttpServletRequest request, final HttpServletResponse response) {
		final Device resourceById = RestPreconditions.checkNotNull(this.service
				.getById(id));

		this.eventPublisher.publishEvent(new SingleResourceRetrieved(this,
				request, response));
		return resourceById;
	}

	@RequestMapping(value = "admin/device", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public final void create(@RequestBody final Device resource,
			final HttpServletRequest request, final HttpServletResponse response) {
		RestPreconditions.checkRequestElementNotNull(resource);
		final Long idOfCreatedResource = this.service.create(resource);

		this.eventPublisher.publishEvent(new ResourceCreated(this, request,
				response, idOfCreatedResource));
	}

	@RequestMapping(value = "admin/device", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public final void update(@RequestBody final Device resource) {
		RestPreconditions.checkRequestElementNotNull(resource);
		RestPreconditions.checkNotNull(this.service.getById(resource.getId()));
		this.service.update(resource);
	}

	@RequestMapping(value = "admin/device/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public final void delete(@PathVariable("id") final Long id) {
		this.service.deleteById(id);
	}

}
