package com.guigrasoft.remote.device.dao;


import org.springframework.orm.hibernate3.HibernateTemplate;

import com.guigrasoft.remote.common.dao.ICommonOperations;
import com.guigrasoft.remote.device.model.Device;

public interface DeviceDAO extends ICommonOperations<Device> {

	void setHibernateTemplate(final HibernateTemplate hibernateTemplate);

}
