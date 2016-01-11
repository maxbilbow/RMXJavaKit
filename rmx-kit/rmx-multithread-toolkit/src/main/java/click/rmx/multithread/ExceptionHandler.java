package click.rmx.multithread;

/**
 * Created by Max on 08/01/2016.
 */
public interface ExceptionHandler {
    void run(Exception e, Thread thread);
}
