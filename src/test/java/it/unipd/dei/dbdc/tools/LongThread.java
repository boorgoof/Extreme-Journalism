package it.unipd.dei.dbdc.tools;

/**
 * Utility class that implements {@link Runnable} and sleeps for 1 second.
 */
public class LongThread implements Runnable {

    /**
     * Sleeps for 1 second.
     *
     * @throws IllegalArgumentException If it's interrupted
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
