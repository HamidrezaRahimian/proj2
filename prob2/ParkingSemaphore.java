import java.util.concurrent.Semaphore;

class ParkingGarage {
  private final Semaphore spots;

  public ParkingGarage(int totalSpots) {
    // bruh, this guy manages the tickets to the garage
    spots = new Semaphore(totalSpots);
  }

  public void enter() {
    try {
      // bruh wait ur turn, no spot = u wait here
      spots.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace(); // just in case thread gets bonked
    }
  }

  public void leave() {
    // aight bro done parking, give back the spot
    spots.release();
  }

  // lil extra if you wanna check how many still free
  public int getAvailableSpots() {
    return spots.availablePermits();
  }
}

class Car extends Thread {
  private final ParkingGarage garage;

  public Car(String name, ParkingGarage g) {
    super(name);
    this.garage = g;
    start(); // vroom vroom thread start
  }

  private void tryingEnter() {
    System.out.println(getName() + ": trying to enter");
  }

  private void justEntered() {
    System.out.println(getName() + ": just entered ");
  }

  private void aboutToLeave() {
    System.out.println(getName() + ":                                     about to leave");
  }

  private void left() {
    System.out.println(getName() + ":                                     have been left ");
  }

  @Override
  public void run() {
    while (true) {
      try {
        // cruising around bruh
        sleep((int) (Math.random() * 10000));
      } catch (InterruptedException e) {
      }

      tryingEnter();
      garage.enter();
      justEntered();

      try {
        // chillin in the garage
        sleep((int) (Math.random() * 20000));
      } catch (InterruptedException e) {
      }

      aboutToLeave();
      garage.leave();
      left();
    }
  }
}

public class ParkingSemaphore {
  public static void main(String[] args) {
    // 7 spots in the garage, bruh don't be greedy
    ParkingGarage garage = new ParkingGarage(7);

    // let’s spawn 10 whip threads
    for (int i = 1; i <= 10; i++) {
      new Car("Car " + i, garage);
    }
  }
}
