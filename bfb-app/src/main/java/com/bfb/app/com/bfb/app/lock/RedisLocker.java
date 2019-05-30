package com.bfb.app.com.bfb.app.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLocker implements DistributedLocker {
    private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    RedissonConnector redissonConnector;
    public RLock  getLock(String resourceName){
        RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        return lock;
    }
    @Override
    public <T> T lock(RLock lock, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
         return lock(lock, worker, 100);
    }

    @Override
    public <T> T lock(RLock lock, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        if(lock==null){
            throw new UnableToAquireLockException();
        }
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
                return worker.invokeAfterLockAquire();
        }
        throw new UnableToAquireLockException();

    }
    public  void unLock(RLock lock){
        if(lock!=null){
            lock.unlock();
        }
    }
}
