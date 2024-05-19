package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static class algorithm implements Runnable {
        MathContext mc = new MathContext(1000);
        BigDecimal num;
        int k;
        public algorithm(int k){
            this.k = k;
        }
        @Override
        public void run() {
            // i want to calculate this : [1/16^k][4/(8k+1) -2/(8k+4) -1/(8k+5) -1/(8k+6)]

            BigDecimal fraction1 = new BigDecimal(1).divide(new BigDecimal(16).pow(k),mc);
            BigDecimal fraction2 = new BigDecimal(4).divide(new BigDecimal(8*k+1),mc);
            BigDecimal fraction3 = new BigDecimal(-2).divide(new BigDecimal(8*k+4),mc);
            BigDecimal fraction4 = new BigDecimal(-1).divide(new BigDecimal(8*k+5),mc);
            BigDecimal fraction5 = new BigDecimal(-1).divide(new BigDecimal(8*k+6),mc);

            num = fraction1.multiply((fraction2.add(fraction3).add(fraction4).add(fraction5)),mc);

            addPI(num);
        }
    }
    public static BigDecimal pi;

    public static synchronized void addPI(BigDecimal num) {  //whenever calculate one sentence according to k, add it with pi
        pi = pi.add(num);
    }

    public String calculate (int floatingPoint) {
        // TODO
        pi = new BigDecimal(0);
        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        for (int i = 0; i <= 10000; i++){
            algorithm term = new algorithm(i);
            threadPool.execute(term);
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());;
        }

        pi = pi.setScale(floatingPoint, RoundingMode.DOWN);
        return pi.toPlainString();
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
        PiCalculator pi = new PiCalculator();
        System.out.println(pi.calculate(1000));
    }
}
