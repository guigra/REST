package com.guigrasoft.remote.common.service;

import java.io.Serializable;

import com.guigrasoft.remote.common.dao.ICommonOperations;

/**
 * @author eugenp
 */
public interface IService< T extends Serializable > extends ICommonOperations< T >{

	// get

	// create

	// delete

	void deleteById( final Long id );

}
