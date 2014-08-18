package com.yiting.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ioc.handler.DefaultAnnotationHandler;
import com.ioc.handler.HandlerDecorate;
import com.ioc.handler.IHandler;

import junit.framework.TestCase;

public class AnnotationHandlerTest extends TestCase{
	
	public void testAnnotionHandler(){
		HandlerDecorate handler=new DefaultAnnotationHandler();
		handler.setHandler(null);
		Map<String, Object> beanMap=handler.convert("com.ioc.bean");
		Iterator iterator=beanMap.keySet().iterator();
		while(iterator.hasNext()){
			String key=(String) iterator.next();
			System.out.println(key);
		}
		
	}
	
	
	
	
}
