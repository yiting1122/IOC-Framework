package com.ioc.handler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ioc.annotation.Bean;
import com.ioc.annotation.Param;
import com.ioc.annotation.Service;
import com.ioc.entity.BeanEntity;
import com.ioc.entity.ParamEntity;
import com.ioc.entity.ServiceEntity;


public class DefaultAnnotationHandler extends HandlerDecorate{

	
	
	@Override
	public void setHandler(IHandler handler) {
		// TODO Auto-generated method stub
		super.setHandler(handler);
	}

	@Override
	public Map<String, Object> convert(String packageName) {
		Map<String, Object> map=null;
		if(this.handler!=null){
		map=this.handler.convert(packageName);
		}
		Map<String, Object> beanMap=new HashMap<String, Object>();
		String packageDirName=packageName.replace(".", "/");
		Enumeration<URL> dirs=null;
		try{
			dirs=Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while(dirs.hasMoreElements()){
				URL url=dirs.nextElement();
				File file=new File(url.getFile());
				String[] classes=file.list();
				for(String className:classes){
					className=className.substring(0, className.length()-6);
					String qName=packageName+"."+className;
					Object obj=Class.forName(qName).newInstance();
					boolean flag=obj.getClass().isAnnotationPresent(Bean.class);
					if(flag){
						Bean bean=(Bean)obj.getClass().getAnnotation(Bean.class);
						System.out.println("描述bean的名称:"+bean.name());
                        System.out.println("描述bean的类型:"+qName);
						Method[] methods=obj.getClass().getDeclaredMethods();
						List<ServiceEntity> serviceEntities=new ArrayList<>();
						for(Method m:methods){
							if(m.getAnnotation(Service.class)==null){
								continue;
							}
							System.out.println("方法名称:" +  m.getName());
							Service service=(Service)m.getAnnotation(Service.class);
							Class<?>[] parameterTypes=m.getParameterTypes();
							Annotation[][]annotations=m.getParameterAnnotations();//一个参数可能有多个注解
							List<ParamEntity> paramEntities=new ArrayList<>();
							if(annotations.length>0){
								for(int i=0;i<annotations.length;i++){
									for(int j=0;j<annotations[i].length;j++){
										Param param=(Param)annotations[i][j];
										System.out.println("参数注解的名称"+param.name());
                                        System.out.println("参数的类型"+parameterTypes[j].getName());
										paramEntities.add(new ParamEntity(param.name(),parameterTypes[j].getName()));
									}
								}
							}
							serviceEntities.add(new ServiceEntity(service.name(),m.getName(), paramEntities));
						}
						beanMap.put(bean.name(), new BeanEntity(bean.name(), qName, serviceEntities));
					}
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(map!=null && map.size()!=0){
	        beanMap.putAll(map);
	    }
		return beanMap;
	}

}
