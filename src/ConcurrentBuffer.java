class ConcurrentBuffer<T> {

    private T payload;
    private boolean bufferContainsPayload = false;
    volatile private boolean isCompleted = false;

    synchronized T read() {
        while (!bufferContainsPayload) {
            if (isCompleted) {
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

    synchronized void write(T payload) {
        if (isCompleted) {
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
        this.payload = payload;
        this.notifyAll();
    }

    void setCompleted() {
        isCompleted = true;
    }

}
