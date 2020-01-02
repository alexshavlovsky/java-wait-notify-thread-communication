import java.util.function.Predicate;

class ThreadUtil {

    private ThreadUtil() {
        throw new AssertionError("This class cannot be instantiated");
    }

    static Predicate<Object> notInterrupted = o -> !Thread.currentThread().isInterrupted();

}
