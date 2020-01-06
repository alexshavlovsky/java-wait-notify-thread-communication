package concurrentbuffer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Producer<T> extends AbstractProducer<T> {

    private Producer(ConcurrentBuffer<T> concurrentBuffer, Stream<T> stream) {
        super(concurrentBuffer, stream);
    }

    public static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, Stream<T> stream, String name) {
        return newInstance(new Producer<>(concurrentBuffer, stream), name);
    }

    public static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, T[] array, String name) {
        return newInstance(new Producer<>(concurrentBuffer, Arrays.stream(array)), name);
    }

    @Override
    public void run() {
        List payloads = stream.peek(concurrentBuffer::write)
                .takeWhile(ThreadUtil.notInterrupted).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " produced: " + payloads);
    }

}
