package com.yiting.container;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.ioc.data.IDataContext;
import com.ioc.entity.BeanEntity;
import com.ioc.entity.ServiceEntity;
import com.ioc.factory.AbstractBeanFactory;
import com.ioc.util.ReflectionUtil;

public class DefaultContainer implements IContainer {

	private AbstractBeanFactory factory;

	public DefaultContainer(Map<String, Object> map) {
		System.out.println("----------开始初始化容器---------");
		// 这个默认工厂的读取应该来自配置文件
		// 如果无自定义信息，自动加载默认的工厂实现
		System.out.println("----------开始初始化容器内工厂---------");
		factory = (AbstractBeanFactory) ReflectionUtil.getInstance(map
				.get(factory) == null ? "com.ioc.factory.DefaultBeanFactory"
				: map.get("factory").toString());
		String resource = map.get("resource") == null ? "com.ioc.bean" : map
				.get("resource").toString();
		String cacheContext = map.get("cacheContext") == null ? "com.ioc.data.DefaultBeanCacheContext"
				: map.get("cacheContext").toString();
		String definitionContext = map.get("definitionContext") == null ? "com.ioc.data.DefaultBeanContext"
				: map.get("definitionContext").toString();
		String defaultHandler = map.get("defaultHandler") == null ? "com.ioc.handler.DefaultAnnotationHandler"
				: map.get("defaultHandler").toString();
		String customHandler = map.get("customHandler") == null ? null : map
				.get("customHandler").toString();
		System.out.println("resource"+resource);
		factory.registerBeanDefinition(resource, cacheContext,
				definitionContext, defaultHandler, customHandler);
		System.out.println("----------初始化容器内工厂成功！---------");
	    System.out.println("----------初始化容器成功！---------");

	}

	@Override
	 //这里使用外观模式，container即是factory的外观
	public Object getBean(String name) {
		return factory.getBean(name);
	}

	@Override
	public Object getService(String beanName, String serviceName,Object...args) {
		IDataContext beanDifinitionContext=getBeanDefinitionContext();
		BeanEntity beanEntity=(BeanEntity) beanDifinitionContext.get(beanName);
		List<ServiceEntity> serviceEntities=beanEntity.getServiceEntities();
		String method=null;
		for(ServiceEntity entity:serviceEntities){
			if(entity.getName().equals(serviceName)){
				method=entity.getValue();
				break;
			}
		}
		Object obj=getBean(beanName);
		Object ret=ReflectionUtil.invoke(obj, ReflectionUtil.getMethod(obj, method), args);
		return ret;
	}

	@Override
	public IDataContext getBeanDefinitionContext() {
		return factory.getBeanDefinitionContext();
	}

	@Override
	public BeanEntity getBeanDefinition(String name) {
		return factory.getBeanDefinition(name);
	}

	@Override
	public IDataContext getBeanCacheContext() {
		return factory.getBeanCacheContext();
	}

	@Override
	public void initContainerService() {
		 //TODO：热部署，动态改变容器中服务的状态，需要调用该方法
	}

}
