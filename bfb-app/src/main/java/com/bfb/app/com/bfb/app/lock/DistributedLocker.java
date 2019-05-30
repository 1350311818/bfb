package com.bfb.app.com.bfb.app.lock;

import org.redisson.api.RLock;

/**
 * 获取锁管理类
 */

public interface DistributedLocker {
    /**
     * 获取锁
     *
     * @param lock 锁的实例
     * @param worker       获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(RLock lock, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(RLock lock, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;

}
