package sbu.cs.Semaphore;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
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
                Calendar calendar = Calendar.getInstance(); //for calculate the time
                System.out.println("\"" + getName() + "\"" + " accesses the resource at " + calendar.get(Calendar.HOUR)
                        + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
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
