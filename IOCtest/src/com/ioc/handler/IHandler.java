package com.ioc.handler;

import java.util.Map;

public interface IHandler {
	//扫描指定目录下的资源，转化为服务描述信息
	public Map<String, Object> convert(String packageName);
	
}
