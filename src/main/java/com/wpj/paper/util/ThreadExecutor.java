package com.wpj.paper.util;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {

    private final int size;

    private final HashMap<Integer, ThreadPoolExecutor> taskMap;

    public ThreadExecutor(int size) {
        this.size = size;
        taskMap = new HashMap<>(size);

        for (int i = 0; i < size - 1; i++) {
            taskMap.put(i, new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new PriorityBlockingQueue<>(10240)));
        }
    }

    public void submit(Object code, Runnable task) {
        int slot = code.hashCode() % size;
        taskMap.get(slot).submit(task);
    }

}
