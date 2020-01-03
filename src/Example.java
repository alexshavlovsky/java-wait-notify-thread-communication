public class Example {

    public static void main(String[] args) throws InterruptedException {
        // prepare payloads
        String[] payloads1 = new String[]{"payload 1", "payload 2", "payload 3", "payload 4", "payload 5", "payload 6"};
        String[] payloads2 = new String[]{"payload A", "payload B", "payload C", "payload D"};
        // instantiate a buffer
        ConcurrentBuffer buffer = new ConcurrentBuffer();
        // start consumers
        Consumer.newInstance(buffer, "Consumer 1");
        Consumer.newInstance(buffer, "Consumer 2");
        Consumer.newInstance(buffer, "Consumer 3");
        // start producers
        Thread producer1 = Producer.newInstance(buffer, payloads1, "Producer 1");
        Thread producer2 = Producer.newInstance(buffer, payloads2, "Producer 2");
        // wait for producers are finished
        ThreadUtil.joinAll(producer1, producer2);
        // set isCompleted flag on the buffer to interrupt consumers
        buffer.setCompleted();
    }

}
