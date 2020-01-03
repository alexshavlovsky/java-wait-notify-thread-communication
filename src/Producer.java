import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Producer implements Runnable {
    private final Stream<String> stream;
    private final ConcurrentBuffer concurrentBuffer;

    private Producer(ConcurrentBuffer concurrentBuffer, Stream<String> stream) {
        this.stream = stream;
        this.concurrentBuffer = concurrentBuffer;
    }

    static Thread newInstance(ConcurrentBuffer concurrentBuffer, Stream<String> stream, String name) {
        Thread producer = new Thread(new Producer(concurrentBuffer, stream), name);
        producer.start();
        return producer;
    }

    static Thread newInstance(ConcurrentBuffer concurrentBuffer, String[] array, String name) {
        return newInstance(concurrentBuffer, Arrays.stream(array), name);
    }

    @Override
    public void run() {
        List<String> payloads = stream.peek(concurrentBuffer::write)
                .takeWhile(ThreadUtil.notInterrupted).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " produced: " + payloads);
    }

}
