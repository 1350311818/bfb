package com.bfb.app.com.bfb.app.controller;

import com.bfb.app.com.bfb.app.lock.AquiredLockWorker;
import com.bfb.app.com.bfb.app.lock.DistributedLocker;
import com.bfb.app.com.bfb.app.lock.RedisLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviveController {
    @Autowired
    private DistributedLocker distributedLocker;
    @Autowired
    private RedisLock redisLock;
    @GetMapping("/user/{id}")
    public String findText(){
       RLock rLock= redisLock.getLock("test");
        try {
            distributedLocker.lock(rLock,new AquiredLockWorker<Object>(){

                @Override
                public Object invokeAfterLockAquire() throws Exception {
                    System.out.println("执行方法！");

                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }
        return  "test";
    }
}
