package com.guigrasoft.remote.device.dao;

import org.springframework.stereotype.Repository;

import com.guigrasoft.remote.common.dao.CustomHibernateDaoSupport;
import com.guigrasoft.remote.device.model.Device;

@Repository
public class DeviceDAOImpl extends CustomHibernateDaoSupport<Device> implements
DeviceDAO {

	public DeviceDAOImpl() {
		super(Device.class);
	}

}
