import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Producer implements Runnable {
    private final String[] data;
    private final ConcurrentBuffer concurrentBuffer;

    Producer(ConcurrentBuffer concurrentBuffer, String[] data) {
        this.data = data;
        this.concurrentBuffer = concurrentBuffer;
    }

    @Override
    public void run() {
        List<String> payloads = Arrays.stream(data).peek(concurrentBuffer::write)
                .takeWhile(ThreadUtil.notInterrupted).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + " produced: " + payloads);
    }

}
