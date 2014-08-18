package com.ioc.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.ioc.annotation.Bean;
import com.ioc.data.IDataContext;
import com.ioc.entity.BeanEntity;
import com.ioc.handler.HandlerDecorate;
import com.ioc.util.ReflectionUtil;

public class DefaultBeanFactory extends AbstractBeanFactory {

	@Override
	public void setBeanDefinitionContext(String beanDefinitionContextName,
			String resource) {
		System.out.println("------------注册服务定义信息的存储区----------");
		beanDefinitionContext=(IDataContext) ReflectionUtil.getInstance(beanDefinitionContextName);
		System.out.println("--------使用handler将元数据转换为服务定义信息-----------");
		Map<String, Object> beanDataMap=handlerDecorate.convert(resource);
		System.out.println("---------b奥村服务定义信息----------");
		beanDefinitionContext.initData(beanDataMap);

	}

	@Override
	public void setBeanCacheContext(String beanCacheContextName) {
		System.out.println("---------注册缓存区域--------------");
		beanCacheContext=(IDataContext)ReflectionUtil.getInstance(beanCacheContextName);
		beanCacheContext.initData(new HashMap<String, Object>());
	}

	@Override
	public void setHandler(String defaultHandler, String handlerName) {
		if(defaultHandler==null){
			throw new IllegalArgumentException("defaultHandler is null");
		}
		 System.out.println("-------------注册处理元数据的Handler-----------");
		 handlerDecorate=(HandlerDecorate)ReflectionUtil.getInstance(defaultHandler);
		 if(handlerName!=null){
			 HandlerDecorate newHandler=(HandlerDecorate)ReflectionUtil.getInstance(handlerName);
		     handlerDecorate.setHandler(newHandler);
		 }

	}

	@Override
	public BeanEntity getBeanDefinition(String name) {
		System.out.println("-------------获取"+name+"的服务定义信息-----------");
		return (BeanEntity) beanDefinitionContext.get(name);
	}

	@Override
	public boolean containsBeanCache(String name) {
		if(beanCacheContext.get(name)!=null){
			return true;
		}
		return false;
	}

	@Override
	public Object getBeanCache(String name) {
		
		return beanCacheContext.get(name);
	}

	@Override
    //递归调用，获取注入Bean依赖的Bean
	public Object createBean(BeanEntity beanEntity) {
		System.out.println("-------------根据服务定义信息创建服务提供者"+beanEntity.getName()+"-----------");
		Object obj=ReflectionUtil.getInstance(beanEntity.getType());
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field f:fields){
			Bean bean=f.getAnnotation(Bean.class);
			if(bean!=null){
				f.setAccessible(true);
				try {
					f.set(obj, getBean(bean.name()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return obj;
	}

	@Override
	public Object registerBeanCache(String name, Object obj) {
		System.out.println("-------------将服务提供者"+name+"放入缓存区-----------");
		beanCacheContext.set(name, obj);
		return null;
	}

	@Override
	public IDataContext getBeanDefinitionContext() {
		
		return beanDefinitionContext;
	}

	@Override
	public IDataContext getBeanCacheContext() {
		
		return beanCacheContext;
	}

}
