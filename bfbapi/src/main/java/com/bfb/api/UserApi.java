package com.bfb.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "com-bfb-app" )
public interface UserApi {
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String findById(@PathVariable("id")Long id);
}
