package concurrentbuffer;

import java.util.LinkedList;

public final class ConcurrentQueue<T> extends AbstractConcurrentBuffer<T> {
    private LinkedList<T> queue = new LinkedList<>();
    private int maxSize;

    public ConcurrentQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    synchronized T read() {
        while (queue.size() == 0) {
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
        return queue.pop();
    }

    synchronized void write(T payload) {
        if (isCompleted) {
            Thread.currentThread().interrupt();
            return;
        }
        while (queue.size() == maxSize) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted...");
                return;
            }
        }
        queue.push(payload);
        this.notifyAll();
    }


}
