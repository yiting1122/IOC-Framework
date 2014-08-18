package com.yiting.test;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import com.ioc.bean.Person;
import com.yiting.container.DefaultContainer;
import com.yiting.container.IContainer;

public class ContainerTest{
	private IContainer defaultContainer;
	@Before
	public void init(){
	    Map<String,Object> map=new HashMap<String, Object>();
		defaultContainer=new DefaultContainer(map);
	}
	
	@Test
	public void testGetBean(){
		Person person=(Person) defaultContainer.getBean("person");
		System.out.println(person.ToSomeWhere("ghe"));
		Object ret=defaultContainer.getService("person","ToSomeWhere", "to hunan");
		System.out.println(ret.toString());
		
	}
	
	
//	@Test
//    public void testGetBeanCacheContext(){
//        Object result=defaultContainer.getService("person","ToSomeWhere","China");
//        System.out.println(result);
//        IDataContext beanCacheContext=defaultContainer.getBeanCacheContext();
//        Map<String,Object> map=beanCacheContext.getAll();
//        assertTrue(map.size()==2);
//        assertNotNull(map.get("person"));
//        assertNotNull(map.get("car"));
//        Person person=(Person)map.get("person");
//        String aString= person.ToSomeWhere1("fa");
//        System.out.println(aString.toString()+" ");
//      //  ReflectionUtil.invoke(person, ReflectionUtil.getMethod(person,"TosomeWhere"), "guanzhou");
//    }
	
}
