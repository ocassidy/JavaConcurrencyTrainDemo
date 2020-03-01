package com.train;

public class TrainBProcess extends Thread {
    private String trainName;
    private com.train.TrainTrack trainTrack;

    public TrainBProcess(String trainName,
                         TrainTrack trainTrack) {
        this.trainName = trainName;
        this.trainTrack = trainTrack;
    }

    public void run() {
        //call functions to navigate track sections (inverse of A train)
        try {
            trainTrack.trainBMoveOnToTrack(trainName);
            trainTrack.trainMove14To17();
            trainTrack.navigateCrossroadsFromBTrack();
            trainTrack.trainMoveFrom2To5();
            trainTrack.trainMove5To8();
            trainTrack.navigateCrossroadsFromATrack();
            trainTrack.trainMoveFrom11To14();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //move off track once train has reached end position
        trainTrack.trainBMoveOffTrack(trainName);
    }
}
