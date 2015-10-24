package click.rmx.multithread;

import click.rmx.debug.ExceptionHandler;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by bilbowm on 14/10/2015.
 */
public class RMXThreadGroup extends ThreadGroup {

    private RMXThreadGroup() {
        super("RMXThreadGroup");
    }

    public static class RMXThread extends Thread {

        public RMXThread(ThreadGroup threadGroup, Runnable runnable, String name) {
            super(threadGroup, runnable, name);
        }
    }
    private static RMXThreadGroup threadGroup;// = new ThreadGroup("RMXThreads");
    public static RMXThread newThread(Runnable runnable)
    {
        RMXThread thread = new RMXThread(defaultGroup() ,runnable, "RMXThread");
        return thread;
    }
    public static RMXThread runOnNewThread(ThreadRunner runnable) {
        return runOnNewThread(runnable, null, null);
    }

    public static RMXThread runOnNewThread(ThreadRunner runnable, ThreadCompletionHandler completionHandler) {
        return runOnNewThread(runnable, null, completionHandler);
    }

    public static RMXThread runOnNewThread(ThreadRunner runnable, ExceptionHandler exceptionHandler) {
        return runOnNewThread(runnable, exceptionHandler, null);
    }

    /**
     *
     * @param runnable
     * @param failAction
     * @param completionHandler
     * @return
     */
    public static RMXThread runOnNewThread(ThreadRunner runnable, ExceptionHandler failAction, ThreadCompletionHandler completionHandler) {
        RMXThread thread = thread = newThread(() -> {
            ThreadData threadData = new ThreadData();
            boolean success = false;
            Instant start = Instant.now();
            try {
                runnable.run(Thread.currentThread());
                success = true;
            } catch (Exception e) {
                if (failAction != null)
                    failAction.run(e,Thread.currentThread());
                else {
                    e.printStackTrace();
                    System.exit(1);
                }
            } finally {
                if (completionHandler != null) {
                    completionHandler.run(threadData.end(success));
                }
            }
        });

        thread.start();//.run();
        return thread;
    }

    public static RMXThreadGroup defaultGroup() {
        return threadGroup != null ? threadGroup : (threadGroup = new RMXThreadGroup());
    }



    public static class ThreadData {
        private Thread thread;
        private Duration duration;
        public final Instant startTime;
        private Boolean success;
        private String name;

        public ThreadData()
        {
            startTime = Instant.now();
        }
        public Duration getDuration() {
            return duration;
        }

        public ThreadData end(boolean success)
        {
            this.thread = Thread.currentThread();
            this.success = success;
            duration = Duration.between(startTime,Instant.now());
            return this;
        }

        public boolean isSuccess() {
//            if (success == null)
//                System.err.println("WARNING: Thread did not finish before check!");
            return success == null ? false : success;
        }

        public Thread getThread() {
            return thread;
        }

        @Override
        public String toString() {
            if (thread == null)
                return "THREAD NOT SET: " + super.toString();

            String message = "";
            message += name != null ? name : "Thread " + this.thread.getName();
            message += ": ";
            if (success == null)
                message += "STATE UNKNOWN!!!";
            else
                message +=  (success == true ? "COMPLETED" : "FAILED");

            message += " in " + duration.toString();// + " seconds"
            return message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public interface ThreadCompletionHandler {
        void run(ThreadData threadData);
    }

    @FunctionalInterface
    public interface ThreadRunner {
        void run(Thread thread);
    }

}
