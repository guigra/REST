package com.guigrasoft.remote.device.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@XmlRootElement(name = "device")
public final class Device implements Serializable {
	private static final long serialVersionUID = -2817129135033673162L;

	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	public Device() {
		super();
	}

	public Device(final String nameToSet) {
		super();

		this.name = nameToSet;
	}

	// API

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long idToSet) {
		this.id = idToSet;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String nameToSet) {
		this.name = nameToSet;
	}

	//

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Device other = (Device) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Device [name=" + this.name + "]";
	}

}
