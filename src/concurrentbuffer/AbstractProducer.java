package concurrentbuffer;

import java.util.stream.Stream;

abstract class AbstractProducer<T> implements Runnable {

    final Stream<T> stream;
    final AbstractConcurrentBuffer<T> concurrentBuffer;

    AbstractProducer(AbstractConcurrentBuffer<T> concurrentBuffer, Stream<T> stream) {
        this.stream = stream;
        this.concurrentBuffer = concurrentBuffer;
    }

    static <T> Thread newInstance(AbstractProducer<T> abstractProducer, String name) {
        Thread producer = new Thread(abstractProducer, name);
        producer.start();
        return producer;
    }

}
