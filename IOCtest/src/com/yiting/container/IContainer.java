package com.yiting.container;

import com.ioc.data.IDataContext;
import com.ioc.entity.BeanEntity;

public interface IContainer {
	public Object getBean(String name);
	public Object getService(String beanName,String serviceName,Object...args);
	public IDataContext getBeanDefinitionContext();
	public BeanEntity getBeanDefinition(String name);
	public IDataContext getBeanCacheContext();
	public void initContainerService();
	
}
