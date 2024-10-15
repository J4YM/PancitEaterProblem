import java.util.concurrent.Semaphore;

class PancitEater extends Thread {
    private int id;
    private Semaphore leftChopstick;
    private Semaphore rightChopstick;

    public PancitEater(int id, Semaphore leftChopstick, Semaphore rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 1000));

                // Acquire the chopsticks
                leftChopstick.acquire();
                rightChopstick.acquire();

                // Eat for a random amount of time
                System.out.println("Asian " + id + " is eating pancit.");
                Thread.sleep((int) (Math.random() * 1000));

                // Release the chopsticks
                leftChopstick.release();
                rightChopstick.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        int numAsians = 4;
        Semaphore[] chopsticks = new Semaphore[numAsians];

        for (int i = 0; i < numAsians; i++) {
            chopsticks[i] = new Semaphore(1);
        }
        
        for (int i = 0; i < numAsians; i++) {
            new PancitEater(i, chopsticks[i], chopsticks[(i + 1) % numAsians]).start();
        }
    }
}