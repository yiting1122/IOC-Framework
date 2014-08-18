package com.ioc.bean;

import com.ioc.annotation.Bean;





@Bean(name="car")
public class Car {
    public String DriveToSomeWhere(String address)
    {
        return "Drive to "+address;
    }
}
