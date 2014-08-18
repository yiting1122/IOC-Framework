package com.ioc.bean;

import com.ioc.annotation.Bean;
import com.ioc.annotation.Param;
import com.ioc.annotation.Service;




@Bean(name="zhangsan")
public class ZhangSan {
    @Bean(name="person")
    private Person person;

    @Service(name="ToSomeWhere")
    public String ToSomeWhere(@Param(name="address") String address)
    {
        return person.ToSomeWhere(address);
    }

}
