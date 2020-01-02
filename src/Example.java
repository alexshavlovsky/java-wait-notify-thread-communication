public class Example {

    public static void main(String[] args) throws InterruptedException {
        // prepare payloads
        String[] payloads1 = new String[]{"payload 1", "payload 2", "payload 3", "payload 4", "payload 5", "payload 6"};
        String[] payloads2 = new String[]{"payload A", "payload B", "payload C", "payload D"};
        // instantiate a buffer
        ConcurrentBuffer concurrentBuffer = new ConcurrentBuffer();
        // start consumers
        new Thread(new Consumer(concurrentBuffer), "Consumer 1").start();
        new Thread(new Consumer(concurrentBuffer), "Consumer 2").start();
        new Thread(new Consumer(concurrentBuffer), "Consumer 3").start();
        // start producers
        Thread producer1 = new Thread(new Producer(concurrentBuffer, payloads1), "Producer 1");
        Thread producer2 = new Thread(new Producer(concurrentBuffer, payloads2), "Producer 2");
        producer1.start();
        producer2.start();
        // wait for producers are finished
        producer1.join();
        producer2.join();
        // set interrupted flag on the buffer to interrupt consumers
        concurrentBuffer.interrupt();
    }

}
