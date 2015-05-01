package cp1.ex4;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sophie on 4/23/15.
 */
public class TimerTestSophie {
    public static void main(String[] args) {
        Timer[] timers = new Timer[100];
        final int[] total = {0};
        final boolean[] finished = new boolean[100];
        long timeStart = System.currentTimeMillis() + 2000;
        for (int i=0; i<100; i++) {
            timers[i] = new Timer(i+"");
            System.out.println("Next timer will wait " + (timeStart - System.currentTimeMillis()) + "ms");
            final int unit = i;
            timers[i].schedule(new TimerTask() {
                @Override
                public void run() {
                    total[0]++;
                    finished[unit] = true;
                }
            }, timeStart - System.currentTimeMillis());
        }
        try {
            Thread.sleep(2124);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 100; i++) {
            System.out.print(finished[i] + " ");
        }

        System.out.println();
    }
}