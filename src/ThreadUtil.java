import java.util.Random;
import java.util.function.Predicate;

class ThreadUtil {

    private ThreadUtil() {
        throw new AssertionError("This class cannot be instantiated");
    }

    static Predicate<Object> notInterrupted = o -> !Thread.currentThread().isInterrupted();

    static void joinAll(Thread... threads) throws InterruptedException {
        for (Thread thread : threads) thread.join();
    }

    static void delay(int delayMin, int delayMax) {
        try {
            Thread.sleep(delayMin + new Random().nextInt(delayMax - delayMin));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
