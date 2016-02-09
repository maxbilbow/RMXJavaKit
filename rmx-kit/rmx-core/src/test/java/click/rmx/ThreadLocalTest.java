package click.rmx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Max on 09/02/2016.
 */
@RunWith(JUnit4.class)
public class ThreadLocalTest {

    private static final AtomicInteger nextId = new AtomicInteger(0);;
    ThreadLocal<Integer> threadLocal = new ThreadLocal(){
        @Override
        protected Integer initialValue()
        {
            return nextId.getAndIncrement();
        }
    };

    @Test
    public void test()
    {
        {
            int i = threadLocal.get();
            System.out.println(i);

            i = threadLocal.get();
            System.out.println(i);
        }
        new Thread(()-> {
            int i = threadLocal.get();
            System.out.println(i);

            i = threadLocal.get();
            System.out.println(i);
        }).start();

        new Thread(()-> {
            int i = threadLocal.get();
            System.out.println(i);

            i = threadLocal.get();
            System.out.println(i);
        }).start();

        new Thread(()-> {
            int i = threadLocal.get();
            System.out.println(i);

            i = threadLocal.get();
            System.out.println(i);
        }).start();

        int j = threadLocal.get();
        System.out.println(j);
    }

}
