class PancitEater extends Thread {
    private int id;
    private boolean[] state;
    private boolean[] request;
    private final Object lock;

    public PancitEater(int id, boolean[] state, boolean[] request, Object lock) {
        this.id = id;
        this.state = state;
        this.request = request;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Think for a random amount of time
                Thread.sleep((int) (Math.random() * 1000));

                synchronized (lock) {
                    // Request permission to eat
                    request[id] = true;
                    state[id] = true;

                    // Simulate eating
                    System.out.println("Asian " + id + " is eating pancit.");
                    Thread.sleep((int) (Math.random() * 1000));

                    // Release the chopsticks
                    request[id] = false;
                    state[id] = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        int numAsians = 4;
        boolean[] state = new boolean[numAsians];
        boolean[] request = new boolean[numAsians];
        Object lock = new Object();

        for (int i = 0; i < numAsians; i++) {
            new PancitEater(i, state, request, lock).start();
        }
    }
}
