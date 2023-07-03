package it.unipd.dei.dbdc.tools;

import java.util.Timer;

//Utility class used for the tests
public class LongThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
