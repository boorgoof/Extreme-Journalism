package it.unipd.dei.dbdc.tools;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.src_strategies.MapSplitAnalyzer.AnalyzerArticleThread;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.CallAPIThread;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIInfo;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link ThreadPool}. The order implies it is the last class of parallelism to be tested.
 */
@Order(2)
public class ThreadPoolTest {

    /**
     * Tries to submit various {@link Runnable} to the class.
     */
    @Test
    public void submit()
    {
        assertDoesNotThrow( () ->
        {
            //Using the long threads
            Future<?> future = ThreadPool.submit(new LongThread());
            future.get();

            //CallAPIThreads
            Map<String, Object> map = new TreeMap<>();
            map.put("api-key", GuardianAPIManagerTest.key);
            future = ThreadPool.submit(new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), PathManagerTest.resources_folder+"threadpool/a.json", map));
            future.get();

            //AnalyzeArticleThreads
            String[] params = {"id", "url", "this is the headline", "this is the body", "date", null, null};
            future = ThreadPool.submit(new AnalyzerArticleThread(new Article(params), new TreeMap<>(), new Semaphore(1)));
            future.get();
        });

        //See if the exceptions are thrown when we call future.get()
        assertThrows(ExecutionException.class, () -> {
            Future<?> future = ThreadPool.submit(new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), PathManagerTest.resources_folder+"threadpool/a.json", null));
            future.get();
        });

        assertThrows(ExecutionException.class, () -> {
            Future<?> future = ThreadPool.submit(new AnalyzerArticleThread(new Article(), new TreeMap<>(), new Semaphore(1)));
            future.get();
        });
    }

    /**
     * Checks if the shutdown of the {@link ThreadPool} causes any error. This function is really long,
     * as it uses {@link VeryLongThread} and {@link LongThread}
     */
    @Test
    public void shutdown()
    {
        //Without initializing it
        ThreadPool.shutdown();

        //After one submitted thread
        assertDoesNotThrow(() -> {
                    Future<?> future = ThreadPool.submit(new LongThread());
                    future.get();
                });

        int tot = Runtime.getRuntime().availableProcessors()+1;

        //After many submitted thread, more than the processors
        assertDoesNotThrow(() -> {
            List<Future<?>> l = new ArrayList<>(tot);
            for (int i = 0; i<tot; i++) {
                Future<?> future = ThreadPool.submit(new LongThread());
                l.add(future);
            }
            assertEquals(tot, l.size());
            for (Future<?> future : l) {
                future.get();
            }
            ThreadPool.shutdown();
        });

        // Shutdown before the get
        assertDoesNotThrow(() -> {
            List<Future<?>> l = new ArrayList<>(tot);
            for (int i = 0; i<tot; i++) {
                Future<?> future = ThreadPool.submit(new LongThread());
                l.add(future);
            }
            ThreadPool.shutdown();
            assertEquals(tot, l.size());
            for (Future<?> future : l) {
                future.get();
            }
        });

        //Checks if there is an error when we submit a thread after shutdown
        assertDoesNotThrow(() -> {
            List<Future<?>> l = new ArrayList<>(tot);
            for (int i = 0; i<tot; i++) {
                Future<?> future = ThreadPool.submit(new LongThread());
                ThreadPool.shutdown();
                l.add(future);
            }
            assertEquals(tot, l.size());
            for (Future<?> future : l) {
                future.get();
            }
        });

        //Test to interrupt the thread pool even if there is a thread still running
        Future<?> future = ThreadPool.submit(new VeryLongThread());
        ThreadPool.shutdown();
        assertThrows(ExecutionException.class, future::get);
    }

}
