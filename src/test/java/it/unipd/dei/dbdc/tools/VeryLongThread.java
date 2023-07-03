package it.unipd.dei.dbdc.tools;

//Utility class used for the tests
public class VeryLongThread implements Runnable {

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
