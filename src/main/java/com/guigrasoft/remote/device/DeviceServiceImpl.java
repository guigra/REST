package com.guigrasoft.remote.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.guigrasoft.remote.common.util.RestPreconditions;
import com.guigrasoft.remote.device.dao.DeviceDAO;
import com.guigrasoft.remote.device.model.Device;

/**
 * @author ricardo guillen
 */
// TODO: later on this should not be public
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceDAO dao;

	public DeviceServiceImpl() {
		super();
	}

	// API

	@Override
	public Device getById(final Long id) {
		return this.dao.getById(id);
	}

	@Override
	public Long create(final Device entity) {
		return this.dao.create(entity);
	}

	@Override
	public void update(final Device entity) {
		this.dao.update(entity);
	}

	@Override
	public void delete(final Device entity) {
		this.dao.delete(entity);
	}

	@Override
	public List<Device> getAll() {
		return this.dao.getAll();
	}

	// service specific

	@Override
	public void deleteById(final Long id) {
		Preconditions.checkNotNull(id);

		final Device entity = this.getById(id);
		RestPreconditions.checkNotNull(entity);

		this.delete(entity);
	}

}