class PancitEater extends Thread {
    private int id;

    public PancitEater(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Think for a random amount of time
                Thread.sleep((int) (Math.random() * 1000));

                // Acquire the lower-numbered chopstick first
                synchronized (App.chopsticks[Math.min(id, (id + 1) % 4)]) {
                    synchronized (App.chopsticks[Math.max(id, (id + 1) % 4)]) {
                        // Eat for a random amount of time
                        System.out.println("Asian " + id + " is eating pancit.");
                        Thread.sleep((int) (Math.random() * 1000));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App{
    public static Object[] chopsticks = new Object[4];

    static {
        for (int i = 0; i < 4; i++) {
            chopsticks[i] = new Object();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new PancitEater(i).start();
        }
    }
}
