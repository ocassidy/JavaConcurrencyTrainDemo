package com.train;

class TrainAProcess extends Thread {
    private String trainName;
    private TrainTrack trainTrack;

    public TrainAProcess(String trainName,
                         TrainTrack trainTrack) {
        this.trainName = trainName;
        this.trainTrack = trainTrack;
    }

    public void run() {
        //call functions to navigate track sections (inverse of B train)
        try {
            trainTrack.trainAMoveOnToTrack(trainName);
            trainTrack.trainMove5To8();
            trainTrack.navigateCrossroadsFromATrack();
            trainTrack.trainMoveFrom11To14();
            trainTrack.trainMove14To17();
            trainTrack.navigateCrossroadsFromBTrack();
            trainTrack.trainMoveFrom2To5();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //move off track once train has reached end position
        trainTrack.trainAMoveOffTrack(trainName);
    }
}

