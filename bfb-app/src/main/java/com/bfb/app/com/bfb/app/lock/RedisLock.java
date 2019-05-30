package com.bfb.app.com.bfb.app.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
    private final static String LOCKER_PREFIX = "lock:";
    @Autowired
    RedissonConnector redissonConnector;
    public RLock getLock(String resourceName){
        RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        return lock;
    }
    public  void unLock(RLock lock){
        if(lock!=null){
            lock.unlock();
        }
    }
}
