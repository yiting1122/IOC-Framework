package com.ioc.data;

import java.util.Map;

public class DefaultBeanContext implements IDataContext {

	private Map<String, Object> beanMap;
	
	@Override
	public void initData(Map<String, Object> map) {
		beanMap=map;
	}

	@Override
	public void set(String name, Object obj) {
		beanMap.put(name, obj);
	}

	@Override
	public Object get(String name) {
		return beanMap.get(name);
	}

	@Override
	public Map<String, Object> getAll() {
		return beanMap;
	}

}
