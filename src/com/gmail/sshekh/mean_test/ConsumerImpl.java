package com.gmail.sshekh.mean_test;

import java.util.LinkedList;

public class ConsumerImpl implements Consumer {

    private static final long FIVE_MINUTES_INTERVAL_MILLIS = 300_000L;
    private LinkedList<ConsumedInteger> acceptedNumbers;

    public ConsumerImpl() {
        this.acceptedNumbers = new LinkedList<>();
    }

    @Override
    public void accept(int number) {
        acceptedNumbers.add(new ConsumedInteger(System.currentTimeMillis(), number));
    }

    @Override
    public double mean() {
        ConsumedInteger first = acceptedNumbers.peekFirst();
        if (first == null) {
            return 0.0;
        }

        long lastAddTime = System.currentTimeMillis();
        long timeInterval = lastAddTime - first.getAddTime();

        boolean narrowInterval = timeInterval > FIVE_MINUTES_INTERVAL_MILLIS;

        while (narrowInterval) {
            first = acceptedNumbers.poll();
            if (first == null) {
                break;
            }

            timeInterval = lastAddTime - first.getAddTime();
            narrowInterval = timeInterval > FIVE_MINUTES_INTERVAL_MILLIS;
        }

        double mean = 0.0;
        int amount = 1;
        for (ConsumedInteger acceptedNumbersInTheLast5Minute : acceptedNumbers) {
            mean += (acceptedNumbersInTheLast5Minute.getAddedValue() - mean) / amount;
            amount++;
        }

        return mean;
    }

    private static class ConsumedInteger {
        private long addTime;
        private int addedValue;


        public ConsumedInteger(long addTime, int addedValue) {
            this.addTime = addTime;
            this.addedValue = addedValue;
        }

        public long getAddTime() {
            return addTime;
        }

        public int getAddedValue() {
            return addedValue;
        }
    }
}
