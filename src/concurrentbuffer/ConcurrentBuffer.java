package concurrentbuffer;

public final class ConcurrentBuffer<T> extends AbstractConcurrentBuffer<T> {
    private T payload;
    private boolean bufferContainsPayload;

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
        this.notifyAll();
        bufferContainsPayload = false;
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
        this.notifyAll();
        bufferContainsPayload = true;
        this.payload = payload;
    }

}
