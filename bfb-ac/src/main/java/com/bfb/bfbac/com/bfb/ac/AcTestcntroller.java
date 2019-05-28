package com.bfb.bfbac.com.bfb.ac;

import com.bfb.api.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcTestcntroller {
    @Autowired
    private UserApi userApi;
    @GetMapping("/user/{id}")
    public  String findById(@PathVariable long id){

        return  userApi.findById(id);
    }
}
