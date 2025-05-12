package prob3;

import java.util.concurrent.*;
import java.util.*;

public class ex5 {
    public static void main(String[] args) throws Exception {

        // Create a thread pool with 4 threads
        ExecutorService pool = Executors.newFixedThreadPool(4);

        int start = 1;
        int end = 200000;
        int chunk = 50000;

        List<Future<Integer>> results = new ArrayList<>();

        // Split the work into 4 tasks
        for (int i = start; i < end; i += chunk) {
            int chunkStart = i;
            int chunkEnd = Math.min(i + chunk, end);
            Future<Integer> future = pool.submit(new PrimeCounter(chunkStart, chunkEnd));
            results.add(future);
        }

        // Collect the results
        int totalPrimes = 0;
        for (Future<Integer> result : results) {
            totalPrimes += result.get(); // Wait for each task
        }

        System.out.println("Total prime numbers between 1 and 200000: " + totalPrimes);

        pool.shutdown(); // Stop the thread pool
    }
}

// The Callable task that counts primes in a range
class PrimeCounter implements Callable<Integer> {
    private int start, end;

    public PrimeCounter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) count++;
        }
        System.out.println(Thread.currentThread().getName() + " counted " + count + " primes from " + start + " to " + end);
        return count;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
