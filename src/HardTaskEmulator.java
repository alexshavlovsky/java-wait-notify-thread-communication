import java.util.Random;

class HardTaskEmulator {

    static void shortTask(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
        delayThread(500, 1000);
    }

    private static void delayThread(int delayMin, int delayMax) {
        try {
            Thread.sleep(delayMin + new Random().nextInt(delayMax - delayMin));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
