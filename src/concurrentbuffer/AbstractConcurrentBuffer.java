package concurrentbuffer;

abstract class AbstractConcurrentBuffer<T> {
    boolean isCompleted = false;

    abstract T read();

    abstract void write(T payload);

    synchronized public void setCompleted() {
        isCompleted = true;
        this.notifyAll();
    }

}
