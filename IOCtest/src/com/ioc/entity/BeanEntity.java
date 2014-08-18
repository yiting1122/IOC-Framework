package com.ioc.entity;

import java.util.List;

/**
 * 
 * @author yiting 
 * name 服务提供者名 
 * type 服务提供者实例类型 
 * value 服务提供者实例
 */

public class BeanEntity {
	private String name;
	private String type;
	private Object value;
	private List<ServiceEntity> serviceEntities;

	public BeanEntity() {
	}

	public BeanEntity(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public BeanEntity(String name, String type,
			List<ServiceEntity> serviceEntities) {
		this.name = name;
		this.type = type;
		this.serviceEntities = serviceEntities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<ServiceEntity> getServiceEntities() {
		return serviceEntities;
	}

	public void setServiceEntities(List<ServiceEntity> serviceEntities) {
		this.serviceEntities = serviceEntities;
	}
	
	
	

}
