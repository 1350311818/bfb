package com.bfb.app.com.bfb.app.lock;
/**
 * 获取锁后需要处理的逻辑
 */

public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
    T invokeAfterLockFail() throws Exception;
}
