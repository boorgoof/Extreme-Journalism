package it.unipd.dei.dbdc.tools;

import java.util.concurrent.*;

/**
 * This utility class has the logic to initialize a threadPool with a specified number of threads
 * and return it.
 * It uses the Singleton design pattern.
 *
 * @see ExecutorService
 */
public class ThreadPoolTools {

    /**
     * The {@link ExecutorService} object to pass to the user.
     *
     */
    private static ExecutorService threadPool;

    /**
     * The number of thread to initialize the threadPool with.
     * It is the number of processors minus one, that is the thread which is currently running.
     *
     */
    private static final int threads = Runtime.getRuntime().availableProcessors()-1;

    /**
     * This function uses the Singleton design pattern to return the {@link ExecutorService}
     * with a fixed number of threads equal to the number of processors available minus one.
     * It uses only a thread pool, if this was not terminated.
     *
     * @return An instance of a {@link ExecutorService} with that number of threads.
     */
    public static ExecutorService getExecutor()
    {
        if (threadPool == null || threadPool.isTerminated())
        {
            threadPool = Executors.newFixedThreadPool(threads);
        }
        return threadPool;
    }

}
