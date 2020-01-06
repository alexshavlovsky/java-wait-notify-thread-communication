import concurrentbuffer.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Example {

    public static void main(String[] args) throws InterruptedException {
        // prepare data sources
        Stream<String> source1 = IntStream.rangeClosed(1, 10).mapToObj(i -> "payload " + i);
        String[] source2 = new String[]{"payload A", "payload B", "payload C", "payload D", "payload E"};

        // instantiate a buffer
        ConcurrentBuffer<String> buffer = new ConcurrentBuffer<>();

        // instantiate a consumer strategy
        ConsumerStrategy<String> strategy = payload -> {
            System.out.println(Thread.currentThread().getName() + " consumes: " + payload);
            ThreadUtil.delay(500, 1000);
        };

        // start consumers
        Consumer.newInstance(buffer, strategy, "Consumer 1");
        Consumer.newInstance(buffer, strategy, "Consumer 2");
        Consumer.newInstance(buffer, strategy, "Consumer 3");

        // start producers
        Thread producer1 = Producer.newInstance(buffer, source1, "Producer 1");
        Thread producer2 = Producer.newInstance(buffer, source2, "Producer 2");

        // wait for producers are finished
        ThreadUtil.joinAll(producer1, producer2);

        // set isCompleted flag on the buffer to interrupt consumers
        buffer.setCompleted();
    }

}
