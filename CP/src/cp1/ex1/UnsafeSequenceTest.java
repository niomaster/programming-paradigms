package cp1.ex1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnsafeSequenceTest {
    public static final int THREADS = 5;
    private static final int ELEMENTS = 500;

    public class UnsafeSequenceThread  extends Thread {
        private final UnsafeSequence seq;
        private int result = 0;

        public UnsafeSequenceThread(UnsafeSequence seq) {
            this.seq = seq;
        }

        public int getResult() {
            return result;
        }

        @Override
        public void run() {
            for(int i = 0; i < ELEMENTS; i++) {
                result += seq.getNext();
            }
        }
    }

    @Test
    public void test() {
        UnsafeSequence seq = new UnsafeSequence();
        UnsafeSequenceThread[] threads = new UnsafeSequenceThread[THREADS];

        for(int i = 0; i < THREADS; i++) {
            threads[i] = new UnsafeSequenceThread(seq);
        }

        for(int i = 0; i < THREADS; i++) {
            threads[i].start();
        }

        for(int i = 0; i < THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                assertTrue(false);
            }
        }

        int total = 0;

        for(int i = 0; i < THREADS; i++) {
            total += threads[i].getResult();
        }

        int n = THREADS * ELEMENTS - 1;

        assertEquals((n * (n + 1)) / 2, total);
    }
}
