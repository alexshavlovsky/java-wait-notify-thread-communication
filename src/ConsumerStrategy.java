@FunctionalInterface
public interface ConsumerStrategy<T> {
    void doTheJob(T payload);
}
