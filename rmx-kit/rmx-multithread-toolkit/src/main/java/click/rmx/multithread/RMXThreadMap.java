package click.rmx.multithread;

import click.rmx.debug.ExceptionHandler;
import click.rmx.multithread.RMXThreadGroup.ThreadRunner;

import java.util.HashMap;

import static click.rmx.multithread.RMXThreadGroup.ThreadCompletionHandler;

/**
 * Created by bilbowm on 14/10/2015.
 */
public class RMXThreadMap<T> extends HashMap<T, Thread> {

    private Thread mainThread;

    private ExceptionHandler defaultFailAction = null;

    private ThreadCompletionHandler defaultCompletionHandler = null;

    public RMXThreadMap()
    {
        super();
    }

    public RMXThreadMap(ExceptionHandler exceptionHandler, ThreadCompletionHandler completionHandler)
    {
        super();
        this.defaultCompletionHandler = completionHandler;
        this.defaultFailAction = exceptionHandler;
    }

    public Thread getMainThread() {
        return mainThread;
    }

    /**
     *
     * @param runnable
     * @param failAction optional
     * @param threadId if threadId == NULL then runnable will run on mainThread
     * @return
     */
    public Thread runOnAfterThread(ThreadRunner runnable, ExceptionHandler failAction, ThreadCompletionHandler completionHandler, T threadId) {
        Thread thread = threadId != null ? this.get(threadId) : mainThread;
        if (thread != null && thread.isAlive())
            try {
                thread.join();
            } catch (InterruptedException e) {
                if (failAction != null)
                    failAction.run(e,thread);
                else {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        thread = RMXThreadGroup.runOnNewThread(runnable, failAction, completionHandler);
        if (threadId != null) {
            this.put(threadId, thread);
            return thread;
        }
        else
            return mainThread = thread;

    }

    public Thread runOnAfterMainThread(ThreadRunner runnable)
    {
        return runOnAfterThread(runnable, defaultFailAction, defaultCompletionHandler, null);
    }

    public Thread runOnAfterMainThread(ThreadRunner runnable, ExceptionHandler exceptionHandler)
    {
        return runOnAfterThread(runnable, exceptionHandler, defaultCompletionHandler, null);
    }
    public Thread runOnAfterMainThread(ThreadRunner runnable, ExceptionHandler exceptionHandler, ThreadCompletionHandler completionHandler) {
        return runOnAfterThread(runnable, exceptionHandler, completionHandler,  null);
    }

    public Thread runOnAfterThread(ThreadRunner runnable)
    {
        return runOnAfterThread(runnable, defaultFailAction, defaultCompletionHandler, null);
    }
    public Thread runOnAfterThread(ThreadRunner runnable, T threadId) {
        return runOnAfterThread(runnable, defaultFailAction, defaultCompletionHandler, threadId);
    }

    public Thread ronOnAfterThread(ThreadRunner runnable, ExceptionHandler failAction)
    {
        return runOnAfterThread(runnable, failAction, defaultCompletionHandler, null);
    }

    public Thread runOnNewThread(ThreadRunner runnable, ExceptionHandler failAction, ThreadCompletionHandler completionHandler)
    {
        return RMXThreadGroup.runOnNewThread(runnable,failAction, completionHandler);
    }


    public boolean hasDefaultFailAction() {
        return defaultFailAction != null;
    }

    public void setDefaultFailAction(ExceptionHandler defaultFailAction) {
        this.defaultFailAction = defaultFailAction;
    }

    public void setDefaultCompletionHandler(ThreadCompletionHandler defaultCompletionHandler) {
        this.defaultCompletionHandler = defaultCompletionHandler;
    }
}
