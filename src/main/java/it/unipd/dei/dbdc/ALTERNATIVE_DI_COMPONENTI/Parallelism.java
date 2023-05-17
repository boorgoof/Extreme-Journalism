package it.unipd.dei.dbdc.ALTERNATIVE_DI_COMPONENTI;

public class Parallelism {
    public static void main(String[] args) {
        Thread new_t = new Thread("B");

        // Per farlo partire si usa start, che fa andare avanti il thread e lo fa terminare, liberando tutto
        // (tranne le variabili condivise, a meno che non siano null).
        new_t.start();

        // Una volta finito, l'oggetto è inutilizzabile

        MyThread p1 = new MyThread("A");
        MyThread p2 = new MyThread("B");

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);

        t1.start();
        t2.start();
    }
}

// Thread è una classe con un metodo run che di fatto non fa nulla.
// Per far funzionare il nostro thread si deve usare una sottoclasse, in cui ridefinire il metodo.
// L'alternativa, migliore, e' fare in modo che implementi Runnable (interfaccia implementata anche da Thread)
// In quel caso, pero', la devo passare al costruttore di thread, perche' solo la classe thread puo' funzionare come thread.

// Di solito si mette come variabile membro un istanza di Thread, che serve per poter fare start anche sull'oggetto MyThread che implementa Runnable
class MyThread implements Runnable
{
    String msg;
    public MyThread(String a)
    {
        msg = a;
    }
    public void run()
    {
        for (int i = 0; i<10; i++)
        {
            System.out.print(msg);

            // Per far aspettare
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}