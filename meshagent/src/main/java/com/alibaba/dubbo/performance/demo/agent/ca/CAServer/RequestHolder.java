package com.alibaba.dubbo.performance.demo.agent.ca.CAServer;

import io.netty.util.concurrent.Promise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 负责全局唯一ID生成
 * 负责ID 与 阻塞线程 对应
 */
public class RequestHolder {
    private static final AtomicLong ID = new AtomicLong(0);
    public static ConcurrentHashMap<Long, Promise> holder =
            new ConcurrentHashMap<>(256);

    private RequestHolder() {
    }

    public static long getId() {
        return ID.getAndIncrement();
    }
}
