class OneSecondTask<T> implements ConsumerStrategy<T> {

    @Override
    public void doTheJob(T payload) {
        System.out.println(Thread.currentThread().getName() + ": " + payload);
        ThreadUtil.delay(500, 1000);
    }

}
