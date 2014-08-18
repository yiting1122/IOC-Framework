package com.ioc.entity;

import java.util.List;
/**
 * 
 * @author kingsoft
 * name //服务名
 * value //服务对应的方法名
 */
public class ServiceEntity {
	public String name;
	public String value;
	private List<ParamEntity> paramEntities;

	public ServiceEntity() {
	}

	public ServiceEntity(String name, String value,List<ParamEntity> paramEntities) {

		this.name = name;
		this.value = value;
		this.paramEntities = paramEntities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<ParamEntity> getParamEntities() {
		return paramEntities;
	}

	public void setParamEntities(List<ParamEntity> paramEntities) {
		this.paramEntities = paramEntities;
	}
	
	
	

}
