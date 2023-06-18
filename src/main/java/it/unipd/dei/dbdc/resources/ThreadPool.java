package it.unipd.dei.dbdc.resources;

import java.util.concurrent.*;

public class ThreadPool {

    private static ExecutorService threadPool;

    private static final int threads = Runtime.getRuntime().availableProcessors()-1;

    public static ExecutorService getExecutor()
    {
        if (threadPool == null || threadPool.isTerminated())
        {
            threadPool = Executors.newFixedThreadPool(threads);
        }
        return threadPool;
    }

}
