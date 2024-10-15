class PancitEater extends Thread {
    private int id;
    private Waiter waiter;

    public PancitEater(int id, Waiter waiter) {
        this.id = id;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Think for a random amount of time
                Thread.sleep((int) (Math.random() * 1000));

                // Request permission to eat from the waiter
                waiter.requestChopsticks(id);

                // Eat for a random amount of time
                System.out.println("Asian " + id + " is eating pancit.");
                Thread.sleep((int) (Math.random() * 1000));

                // Return the chopsticks to the waiter
                waiter.returnChopsticks(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Waiter {
    private boolean[] chopsticksAvailable;

    public Waiter(int numAsians) {
        chopsticksAvailable = new boolean[numAsians];
        for (int i = 0; i < numAsians; i++) {
            chopsticksAvailable[i] = true;
        }
    }

    public synchronized void requestChopsticks(int id) {
        while (!chopsticksAvailable[id] || !chopsticksAvailable[(id + 1) % chopsticksAvailable.length]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chopsticksAvailable[id] = false;
        chopsticksAvailable[(id + 1) % chopsticksAvailable.length] = false;
    }

    public synchronized void returnChopsticks(int id) {
        chopsticksAvailable[id] = true;
        chopsticksAvailable[(id + 1) % chopsticksAvailable.length] = true;
        notifyAll();
    }
}

public class App {
    public static void main(String[] args) {
        int numAsians = 4;
        Waiter waiter = new Waiter(numAsians);

        for (int i = 0; i < numAsians; i++) {
            new PancitEater(i, waiter).start();
        }
    }
}