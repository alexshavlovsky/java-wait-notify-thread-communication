import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Producer<T> implements Runnable {
    private final Stream<T> stream;
    private final ConcurrentBuffer<T> concurrentBuffer;

    private Producer(ConcurrentBuffer<T> concurrentBuffer, Stream<T> stream) {
        this.stream = stream;
        this.concurrentBuffer = concurrentBuffer;
    }

    static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, Stream<T> stream, String name) {
        Thread producer = new Thread(new Producer<>(concurrentBuffer, stream), name);
        producer.start();
        return producer;
    }

    static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, T[] array, String name) {
        return newInstance(concurrentBuffer, Arrays.stream(array), name);
    }

    @Override
    public void run() {
        List payloads = stream.peek(concurrentBuffer::write)
                .takeWhile(ThreadUtil.notInterrupted).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " produced: " + payloads);
    }

}
