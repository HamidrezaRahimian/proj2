package prob3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ex1 {
    public static void main(String[] args) {

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3); //cap = 3

        Producer producer = new Producer(queue);
        User User = new User(queue);

        producer.start();
        User.start();
    }

}

// THREADS------------------------------------------------------------------


class Producer extends Thread {
    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Provider:        trying to put item " + i);
                queue.put("Item " + i);  // waits if queue is full
                System.out.println("Provider:        put item " + i);
                Thread.sleep(1000); // simulate some delay
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class User extends Thread {
    private BlockingQueue<String> queue;

    public User(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.sleep(1000); // wait before taking
                String item = queue.take(); // waits if queue is empty
                System.out.println("User :           took " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}