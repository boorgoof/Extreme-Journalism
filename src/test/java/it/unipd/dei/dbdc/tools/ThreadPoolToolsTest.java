package it.unipd.dei.dbdc.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolToolsTest {

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
