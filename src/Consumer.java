import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consumer<T> implements Runnable {

    private final ConcurrentBuffer<T> concurrentBuffer;
    private final ConsumerStrategy<T> strategy;

    private Consumer(ConcurrentBuffer<T> concurrentBuffer, ConsumerStrategy<T> strategy) {
        this.concurrentBuffer = concurrentBuffer;
        this.strategy = strategy;
    }

    static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, ConsumerStrategy<T> task, String name) {
        Thread consumer = new Thread(new Consumer<>(concurrentBuffer, task), name);
        consumer.start();
        return consumer;
    }

    @Override
    public void run() {
        List payloads = Stream.generate(concurrentBuffer::read).takeWhile(ThreadUtil.notInterrupted).
                peek(strategy::doTheJob).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " consumed: " + payloads);
    }

}
