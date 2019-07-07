package com.alibaba.dubbo.performance.demo.agent.utils;

import java.util.concurrent.ExecutionException;

public interface Registry {
    void start();

    void find() throws ExecutionException, InterruptedException;

}
