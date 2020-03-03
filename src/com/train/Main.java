package com.train;

public class Main {
    private static TrainTrack trainTrack = new TrainTrack();

    public static void main(String[] args) throws InterruptedException {
        //specify number of A and B trains
        final int numOfATrains = 10;
        final int numOfBTrains = 10;

        //create arrays to hold the trains
        TrainAProcess[] trainAProcess = new TrainAProcess[numOfATrains];
        TrainBProcess[] trainBProcess = new TrainBProcess[numOfBTrains];

        //create trains go enter the track
        for (int i = 0; i < numOfATrains; i++) {
            trainAProcess[i] = new TrainAProcess("A" + i, trainTrack);
        }

        for (int i = 0; i < numOfBTrains; i++) {
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
            trainAProcess[i].join();
        }

        for (int i = 0; i < numOfBTrains; i++) {
            trainBProcess[i].join();
        }
        //print track
        trainTrack.activity.printActivities();
    }
}
