package cp1.ex5;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatTest {
    public static final int THREADS = 5;

    public class FormatterThread extends Thread {
        private final SimpleDateFormat format;

        public FormatterThread(SimpleDateFormat format) {
            this.format = format;
        }

        @Override
        public void run() {
            for(int i = 0; i < 1000000; i++) {
                int year = (int) (Math.random() * 10) + 10;
                int month = (int) (Math.random() * 10) + 1;
                int date = (int) (Math.random() * 20) + 1;
                String ding = format.format(new Date(year - 1900, month - 1, date));
                try {
                    assert ding.equals(year + "-" + month + "-" + date + "T00:00:00");
                } catch(AssertionError e) {
                    synchronized(format) {
                        System.out.print(year + " ");
                        System.out.print(month + " ");
                        System.out.print(date + " ");
                        System.out.println(ding);
                        return;
                    }
                }
            }
        }
    }

    @Test
    public void simpleDateFormatTest() throws InterruptedException {
        SimpleDateFormat format = new SimpleDateFormat("y-M-d'T'HH:mm:ss");

        FormatterThread[] threads = new FormatterThread[THREADS];
        for(int i = 0; i < THREADS; i++) { threads[i] = new FormatterThread(format); }
        for(int i = 0; i < THREADS; i++) { threads[i].start(); }
        for(int i = 0; i < THREADS; i++) { threads[i].join(); }
    }
}
