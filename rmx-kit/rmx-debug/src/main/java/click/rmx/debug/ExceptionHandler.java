package click.rmx.debug;

/**
 * Created by bilbowm on 15/10/2015.
 */
@FunctionalInterface
public interface ExceptionHandler {
    void run(Exception e, Thread t);
}
