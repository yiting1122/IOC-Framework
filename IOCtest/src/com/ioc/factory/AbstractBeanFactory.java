package com.ioc.factory;

import com.ioc.data.IDataContext;
import com.ioc.entity.BeanEntity;
import com.ioc.handler.HandlerDecorate;

public abstract class AbstractBeanFactory implements IBeanFactory {
	protected IDataContext beanDefinitionContext;
	protected IDataContext beanCacheContext;
	protected HandlerDecorate handlerDecorate;

	public abstract void setBeanDefinitionContext(
			String beanDefinitionContextName, String resource);

	public abstract void setBeanCacheContext(String beanCacheContextName);

	public abstract void setHandler(String defaultHandler, String handlerName);

	@Override
	public Object getBean(String name) {
		BeanEntity beanEntity = getBeanDefinition(name);
		if (containsBeanCache(name)) {
			return getBeanCache(name);
		}
		Object beanObject = createBean(beanEntity);
		registerBeanCache(name, beanObject);
		return beanObject;
	}

	 @Override
	    //模板方法
	    //注册服务组件，初始化服务描述信息
	public final void registerBeanDefinition(String resource, String cacheContext,
			String definitionContext, String defaultHandler,
			String customHandler) {
		this.setHandler(defaultHandler, customHandler);
		this.setBeanDefinitionContext(definitionContext, resource);
		this.setBeanCacheContext(cacheContext);
		
	}

	public abstract BeanEntity getBeanDefinition(String name);

	public abstract boolean containsBeanCache(String name);

	public abstract Object getBeanCache(String name);

	public abstract Object createBean(BeanEntity beanEntity);

	public abstract Object registerBeanCache(String name, Object obj);

	// 获取所有的服务描述信息
	public abstract IDataContext getBeanDefinitionContext();

	// 获取所有的服务实例缓存信息
	public abstract IDataContext getBeanCacheContext();
}
