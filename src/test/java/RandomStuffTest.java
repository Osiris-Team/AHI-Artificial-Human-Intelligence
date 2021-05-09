/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RandomStuffTest {

    public static AtomicLong iterationsPerSecond = new AtomicLong();

    @Test
    void testIterationSpeed() throws InterruptedException {
        // Create a new thread for printing and resetting the counter
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Iterations per second: " + iterationsPerSecond);
                    iterationsPerSecond.set(0);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();

        // For 1 thread: 155203737
        // For 2 thread: 99928724
        // For 3 thread: 82243906
        // For loops are faster than while loops
        Thread t1 = new Thread(() -> {
            while (true) {
                for (long i = 0; i < Long.MAX_VALUE; i++) {
                    iterationsPerSecond.incrementAndGet();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                for (long i = 0; i < Long.MAX_VALUE; i++) {
                    iterationsPerSecond.incrementAndGet();
                }
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            while (true) {
                for (long i = 0; i < Long.MAX_VALUE; i++) {
                    iterationsPerSecond.incrementAndGet();
                }
            }
        });
        t3.start();

        while (t1.isAlive() || t2.isAlive() || t3.isAlive())
            Thread.sleep(1000);

    }

    @Test
    void testMapsFunctionalty() {
        Map<Integer, String> indexAndStringMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            indexAndStringMap.put(i, "Index should be " + i);
        }

        for (int i :
                indexAndStringMap.keySet()) {
            System.out.println("Index: " + i + " " + indexAndStringMap.get(i));
        }
    }
}
