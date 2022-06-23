package com.gmail.sshekh.mean_test;

import com.gmail.sshekh.mean_test.impl.ConsumerImpl;

import java.util.concurrent.ThreadLocalRandom;

public class MeanTaskMain {
    public static void main(String[] args) throws InterruptedException {
        Consumer consumer = new ConsumerImpl();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000_000; i++) {
            consumer.accept(ThreadLocalRandom.current().nextInt(-3213245, 55443544));
            Thread.sleep(2);
            int timeDif = ThreadLocalRandom.current().nextInt(80000, 100000);
            long cur = System.currentTimeMillis();
            if (cur - start > timeDif) {
                long meanCount = System.currentTimeMillis();
                double mean = consumer.mean();
                start = System.currentTimeMillis();
                System.out.println("Mean calc is: " + (start - meanCount));
                System.out.printf("Mean(average) is = '%f'\n", mean);
            }
        }
    }
}