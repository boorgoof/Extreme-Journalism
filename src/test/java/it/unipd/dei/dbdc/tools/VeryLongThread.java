package it.unipd.dei.dbdc.tools;

/**
 * Utility class that implements {@link Runnable} and sleeps for 70 seconds.
 */
public class VeryLongThread implements Runnable {

    /**
     * The only constructor of the class. It does nothing.
     */
    public VeryLongThread() {}

    /**
     * Sleeps for 70 seconds.
     *
     * @throws IllegalArgumentException If it's interrupted
     */
    @Override
    public void run() {
        try {
            //70 seconds
            Thread.sleep(70000);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
