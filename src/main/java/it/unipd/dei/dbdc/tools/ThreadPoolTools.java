package it.unipd.dei.dbdc.tools;

import java.util.concurrent.*;

public class ThreadPoolTools {

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
