import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// This class represents the parking garage
class ParkingGarage {
    private final BlockingQueue<Object> spaces;

    // Constructor takes the total number of parking spots
    public ParkingGarage(int capacity) {
        // Using ArrayBlockingQueue to manage the parking slots
        spaces = new ArrayBlockingQueue<>(capacity);

        // Fill the queue with 'tokens' representing available spots
        for (int i = 0; i < capacity; i++) {
            spaces.add(new Object()); // each object = one free parking spot
        }
    }

    // Car tries to enter the garage
    public void enter(String carName) {
        System.out.println(carName + ": trying to enter");

        try {
            // 'take()' will wait if no spots are available
            spaces.take();
            System.out.println(carName + ": just entered");
        } catch (InterruptedException e) {
            // In case thread gets interrupted, exit cleanly
            Thread.currentThread().interrupt();
        }
    }

    // Car leaves the garage
    public void leave(String carName) {
        System.out.println(carName + ":                                     about to leave");

        try {
            // 'put()' will add a spot back to the queue (garage)
            spaces.put(new Object());
            System.out.println(carName + ":                                     have been left");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Each car is a thread trying to use the garage
class Car extends Thread {
    private final ParkingGarage garage;

    public Car(String name, ParkingGarage garage) {
        super(name); // thread name is car name
        this.garage = garage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simulating time spent driving before parking
                sleep((int)(Math.random() * 10000));
            } catch (InterruptedException e) {
                return;
            }

            // Try to enter the parking garage
            garage.enter(getName());

            try {
                // Simulating time spent parked
                sleep((int)(Math.random() * 20000));
            } catch (InterruptedException e) {
                return;
            }

            // Leave the garage
            garage.leave(getName());
        }
    }
}

// Main class to run the simulation
public class ParkingBlockingQueue {
    public static void main(String[] args) {
        // Garage has 7 spots available
        ParkingGarage garage = new ParkingGarage(7);

        // Create and start 10 car threads
        for (int i = 1; i <= 10; i++) {
            new Car("Car " + i, garage).start();
        }
    }
}
