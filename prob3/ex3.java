package prob3;

import java.util.concurrent.atomic.AtomicInteger;

public class ex3 {
    public static void main(String[] args) {
        SharedLikesCounter counter = new SharedLikesCounter();

        // Create 2 users clicking like and unlike
        new UserA(counter).start();
        new UserB(counter).start();
    }
}

// The Shared Counter using AtomicInteger________________________________________________________
class SharedLikesCounter {
    AtomicInteger likes = new AtomicInteger(50); // start with 50 likes

    public void addLike(String name) {
        int before = likes.getAndAdd(1); // add 1, return old value
        System.out.println(name + " added a like. Before: " + before + " After: " + likes.get());
    }

    public void removeLike(String name) {
        int after = likes.addAndGet(-1); // remove 1, return new value
        System.out.println(name + " removed a like. New total: " + after);
    }
}

// One user that keeps liking the post________________________________________________________________
class UserA extends Thread {
    SharedLikesCounter counter;

    public UserA(SharedLikesCounter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            counter.addLike("UserA");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Another user who unlikes it________________________________________________________________________
class UserB extends Thread {
    SharedLikesCounter counter;

    public UserB(SharedLikesCounter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 2; i++) {
            counter.removeLike("UserB");
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
