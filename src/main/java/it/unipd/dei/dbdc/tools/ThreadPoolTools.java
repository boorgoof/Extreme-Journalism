package it.unipd.dei.dbdc.tools;

import java.util.concurrent.*;

/**
 * This utility class has the logic to initialize a threadPool with a specified number of threads
 * and use it to run Runnable objects.
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
     * This function has the logic to submit to an {@link ExecutorService} the {@link Runnable}
     * passed as a parameter.
     *
     * @param r A {@link Runnable} to submit.
     * @return A {@link Future} obtained by submitting the {@link Runnable} to the {@link ExecutorService}.
     */
    public static Future<?> submit(Runnable r) {
            if (threadPool == null || threadPool.isShutdown()) {
                threadPool = Executors.newFixedThreadPool(threads);
            }
            return threadPool.submit(r);
        }

    /**
     * This function has the logic to shut down the {@link ExecutorService},
     * waiting 60 seconds if it all the other threads have not finished yet.
     *
     */
    public static void shutdown()
    {
        if (threadPool == null || threadPool.isShutdown())
        {
            return;
        }
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
