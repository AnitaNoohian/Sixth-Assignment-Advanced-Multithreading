package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Operator extends Thread {

    Semaphore semaphore;

    public Operator(String name, Semaphore semaphore) {
        super(name);
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            try {
                semaphore.acquire();
                System.out.println(getName());
                Resource.accessResource();          // critical section - a Maximum of 2 operators can access the resource concurrently
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                semaphore.release();
            }

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
