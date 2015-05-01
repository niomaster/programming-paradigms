package cp1.ex2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueueTest {
    public static final int PRODUCERS = 2;
    public static final int MAX_VALUE = 500;
    public static final int CONSUMERS = 2;

    public class Producer extends Thread {
        private final Queue<Integer> channel;
        private Throwable error;

        public Producer(Queue<Integer> channel) {
            this.channel = channel;
        }

        public Throwable getError() {
            return error;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= MAX_VALUE; i++) {
                    channel.push(i);
                }
            } catch(Throwable t) {
                t.printStackTrace();
                error = t;
            }
        }
    }

    public class Consumer extends Thread {
        private final Queue<Integer> channel;
        private int result = 0;
        private Throwable error;

        public Consumer(Queue<Integer> channel) {
            this.channel = channel;
        }

        public int getResult() {
            return result;
        }

        public Throwable getError() {
            return error;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    int message = channel.pull();

                    if (message == -1) {
                        break;
                    } else {
                        result += message;
                    }
                }
            } catch(Throwable t) {
                t.printStackTrace();
                error = t;
            }
        }
    }

    @Test
    public void test() {
        Queue<Integer> channel = new UnsafeQueue<Integer>();

        Producer[] producers = new Producer[PRODUCERS];
        Consumer[] consumers = new Consumer[CONSUMERS];

        for(int i = 0; i < PRODUCERS; i++) { producers[i] = new Producer(channel); }
        for(int i = 0; i < CONSUMERS; i++) { consumers[i] = new Consumer(channel); }

        int total = 0;
        Throwable error = null;

        try {
            for(int i = 0; i < PRODUCERS; i++) { producers[i].start(); }
            for(int i = 0; i < CONSUMERS; i++) { consumers[i].start(); }

            for(int i = 0; i < PRODUCERS; i++) { producers[i].join(); }
            for(int i = 0; i < CONSUMERS; i++) { channel.push(-1); }
            for(int i = 0; i < CONSUMERS; i++) { consumers[i].join(); }


            for(int i = 0; i < CONSUMERS; i++) {
                total += consumers[i].getResult();
            }


        } catch(Throwable t) {
            error = t;
        }

        for(int i = 0; i < PRODUCERS; i++) { assertEquals(null, producers[i].getError()); }
        for(int i = 0; i < CONSUMERS; i++) { assertEquals(null, consumers[i].getError()); }

        assertEquals(PRODUCERS * MAX_VALUE * (MAX_VALUE+1) / 2, total);

        assertEquals(null, error);
    }
}
