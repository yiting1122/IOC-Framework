package com.ioc.data;

import java.util.Map;

public interface IDataContext {
	public void initData(Map<String, Object> map);
	public void set(String name,Object obj);
	public Object get(String name);
	public Map<String, Object> getAll();
}
