class ConcurrentBuffer {

    private String payload;
    private boolean bufferContainsPayload = false;
    private boolean isInterrupted = false;

    synchronized String read() {
        while (!bufferContainsPayload) {
            if (isInterrupted) {
                Thread.currentThread().interrupt();
                return null;
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted...");
                return null;
            }
        }
        bufferContainsPayload = false;
        this.notifyAll();
        return payload;
    }

    synchronized void write(String res) {
        if (isInterrupted) {
            Thread.currentThread().interrupt();
            return;
        }
        while (bufferContainsPayload) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted...");
                return;
            }
        }
        bufferContainsPayload = true;
        payload = res;
        this.notifyAll();
    }

    synchronized void interrupt() {
        isInterrupted = true;
    }

}
