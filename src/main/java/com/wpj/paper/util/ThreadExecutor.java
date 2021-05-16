package com.wpj.paper.util;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {

    private int size;

    private HashMap<Integer, ThreadPoolExecutor> taskMap;

    public ThreadExecutor(int size) {
        this.size = size;
        taskMap = new HashMap<>(size);

        for (int i = 0; i < size - 1; i++) {
            taskMap.put(i, new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
        }
    }

    public void submit(Object code, Runnable task) {
        int slot = code.hashCode() % size;
        taskMap.get(slot).submit(task);
    }

}
