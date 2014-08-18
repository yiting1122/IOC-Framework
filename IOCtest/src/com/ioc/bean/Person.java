package com.ioc.bean;

import com.ioc.annotation.Bean;
import com.ioc.annotation.Param;
import com.ioc.annotation.Service;


@Bean(name="person")
public class Person {
    @Bean(name="car")
    private Car car;

    @Service(name="ToSomeWhere")
    public String ToSomeWhere(@Param(name="address") String address)
    {
        return car.DriveToSomeWhere(address);
    }

    @Service(name="ToSomeWhere1")
    public String ToSomeWhere1(@Param(name="address1") String address1)
    {
        return car.DriveToSomeWhere(address1);
    }

}
