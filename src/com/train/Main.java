package com.train;

public class Main {
    private static TrainTrack trainTrack = new TrainTrack();

    public static void main(String[] args) {
        //specify number of A and B trains
        final int numOfATrains = 4;
        final int numOfBTrains = 4;

        //create arrays to hold the trains
        TrainAProcess[] trainAProcess = new TrainAProcess[numOfATrains];
        TrainBProcess[] trainBProcess = new TrainBProcess[numOfBTrains];

        //create trains go enter the track
        for (int i = 0; i < numOfATrains; i++) {
            CDS.idleQuietly((int) (Math.random() * 1000));
            trainAProcess[i] = new TrainAProcess("A" + i, trainTrack);
        }

        for (int i = 0; i < numOfBTrains; i++) {
            CDS.idleQuietly((int) (Math.random() * 1000));
            trainBProcess[i] = new TrainBProcess("B" + i, trainTrack);
        }

        //start train processes
        for (int i = 0; i < numOfATrains; i++) {
            trainAProcess[i].start();
        }

        for (int i = 0; i < numOfBTrains; i++) {
            trainBProcess[i].start();
        }

        // trains now on track
        // wait for all the train threads to finish before joining back
        for (int i = 0; i < numOfATrains; i++) {
            try {
                trainAProcess[i].join();
            } catch (InterruptedException ex) {
            }
        }

        for (int i = 0; i < numOfBTrains; i++) {
            try {
                trainBProcess[i].join();
            } catch (InterruptedException ex) {
            }
        }
        //print track
        trainTrack.activity.printActivities();
    }
}