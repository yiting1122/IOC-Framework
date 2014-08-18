package com.ioc.data;

import java.util.Map;

public class DefaultBeanCacheContext implements IDataContext{
	private Map<String, Object> cacheBeanMap;
	@Override
	public void initData(Map<String, Object> map) {
		cacheBeanMap=map;
	}

	@Override
	public void set(String name, Object obj) {
		cacheBeanMap.put(name, obj);
	}

	@Override
	public Object get(String name) {
		return cacheBeanMap.get(name);
	}

	@Override
	public Map<String, Object> getAll() {
		return cacheBeanMap;
	}

}
