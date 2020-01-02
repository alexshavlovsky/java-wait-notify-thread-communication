import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consumer implements Runnable {

    private final ConcurrentBuffer concurrentBuffer;

    Consumer(ConcurrentBuffer concurrentBuffer) {
        this.concurrentBuffer = concurrentBuffer;
    }

    @Override
    public void run() {
        List<String> payloads = Stream.generate(concurrentBuffer::read).takeWhile(ThreadUtil.notInterrupted).
                peek(HardTaskEmulator::shortTask).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " consumed: " + payloads);
    }

}
