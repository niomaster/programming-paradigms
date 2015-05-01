package cp1.ex4;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimerTest {
    private static final int TIMERS = 10000;

    @Test
    public void timerTest() {
        Timer[] timers = new Timer[TIMERS];

        for(int i = 0; i < TIMERS; i++) {
            timers[i] = new Timer();
        }

        final int[] total = {0};
        final int[] threadSafeTotal = {0};

        long runUntil = System.currentTimeMillis() + 1000;

        for(int i = 0; i < TIMERS; i++) {
            timers[i].schedule(new TimerTask() {
                @Override
                public void run() {
                    total[0]++;
                    synchronized(threadSafeTotal) {
                        threadSafeTotal[0]++;
                    }
                }
            }, runUntil - System.currentTimeMillis());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertEquals(TIMERS, threadSafeTotal[0]);
        assertEquals(TIMERS, total[0]);
    }
}
