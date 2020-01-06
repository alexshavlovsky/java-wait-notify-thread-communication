package concurrentbuffer;

abstract class AbstractConsumer<T> implements Runnable {

    final AbstractConcurrentBuffer<T> concurrentBuffer;
    final ConsumerStrategy<T> strategy;

    AbstractConsumer(AbstractConcurrentBuffer<T> concurrentBuffer, ConsumerStrategy<T> strategy) {
        this.concurrentBuffer = concurrentBuffer;
        this.strategy = strategy;
    }

    static <T> Thread newInstance(AbstractConsumer<T> abstractConsumer, String name) {
        Thread consumer = new Thread(abstractConsumer, name);
        consumer.start();
        return consumer;
    }

}
