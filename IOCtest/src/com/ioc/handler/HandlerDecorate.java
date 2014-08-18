package com.ioc.handler;

import java.util.Map;

public abstract class HandlerDecorate implements IHandler {

	protected IHandler handler;
	public void setHandler(IHandler handler){
		this.handler=handler;
	}
	
	
	@Override
	public Map<String, Object> convert(String packageName) {
		if(handler!=null){
			return handler.convert(packageName);
		}
		return null;
		
	}

}
