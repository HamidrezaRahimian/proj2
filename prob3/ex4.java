package prob3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ex4 {
    public static void main(String[] args) {

        // Barrier waits for 3 players
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("All players arrived! Let’s start the game!");
        });

        // Create 3 players
        new Player("Player1", barrier).start();
        new Player("Player2", barrier).start();
        new Player("Player3", barrier).start();
    }
}

class Player extends Thread {
    private String name;
    private CyclicBarrier barrier;

    public Player(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }

    public void run() {
        try {
            System.out.println(name + " is getting ready...");
            Thread.sleep((int)(Math.random() * 3000)); // Simulate time taken to get ready
            // Wait for other players to get ready
            System.out.println(name + " is ready and waiting...");
            barrier.await();
            Thread.sleep((int)( 1000));
            System.out.println(name + " starts the game!");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
