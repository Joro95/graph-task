package com.graph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by developer on 08/10/18.
 */
public class ThreadPoolSquareExecutor {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(NUMBER_OF_CORES);

    private ThreadPoolSquareExecutor() {}

    public static ExecutorService getExecutor() {
        return EXECUTOR_SERVICE;
    }
}
