package com.bowen.middleware.utils;

import java.util.concurrent.ExecutionException;

public interface Registry {
    void start();

    void find() throws ExecutionException, InterruptedException;
}
