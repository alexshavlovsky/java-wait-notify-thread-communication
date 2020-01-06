package concurrentbuffer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Consumer<T> extends AbstractConsumer<T> {

    private Consumer(ConcurrentBuffer<T> concurrentBuffer, ConsumerStrategy<T> strategy) {
        super(concurrentBuffer, strategy);
    }

    public static <T> Thread newInstance(ConcurrentBuffer<T> concurrentBuffer, ConsumerStrategy<T> consumerStrategy, String name) {
        return newInstance(new Consumer<>(concurrentBuffer, consumerStrategy), name);
    }

    @Override
    public void run() {
        List payloads = Stream.generate(concurrentBuffer::read).takeWhile(ThreadUtil.notInterrupted).
                peek(strategy::doTheJob).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " consumed: " + payloads);
    }

}
