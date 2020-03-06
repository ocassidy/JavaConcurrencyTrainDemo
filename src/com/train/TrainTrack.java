package com.train;

import java.util.concurrent.Semaphore;

public class TrainTrack {
    //create track with slots
    private String[] slots = {"[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]",
            "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]"};

    // declare array to hold the binary Semaphores for access to track slots
    private final static Semaphore[] slotSem = new Semaphore[19];
    private final static Semaphore totalTrainSem = new Semaphore(10);
    private final static Semaphore crossroadMutex = new Semaphore(1);

    // create variable to record activity
    Activity activity;

    public TrainTrack() {
        // record the train activity
        activity = new Activity(slots);
        for (int i = 0; i <= 18; i++) {
            // create the array of slotSem and give them all 1 free permit
            slotSem[i] = new Semaphore(1);
        }
    }

    public void trainAMoveOnToTrack(String trainName) throws InterruptedException {
        Thread.sleep((int) (Math.random() * 5));
        // limit number of trains on track to avoid deadlock
        totalTrainSem.acquire();
        // wait for slot 4 & 5 to be free before entering track
        slotSem[4].acquire();
        slotSem[5].acquire();
        slots[5] = "[" + trainName + "]";
        activity.addMovedTo(5);
        slotSem[4].release();
    }

    public void trainBMoveOnToTrack(String trainName) throws InterruptedException {
        Thread.sleep((int) (Math.random() * 10));
        // limit number of trains on track to avoid deadlock
        totalTrainSem.acquire();
        // wait for slot 13 & 14 to be free before entering track
        slotSem[13].acquire();
        slotSem[14].acquire();
        slots[14] = "[" + trainName + "]";
        activity.addMovedTo(14);
        slotSem[13].release();
    }

    public void trainMoveFrom2To5() throws InterruptedException {
        int currentPosition = 2;
        while (currentPosition < 5) {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        }
    }

    public void trainMove5To8() throws InterruptedException {
        int currentPosition = 5;
        while (currentPosition < 8) {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        }
    }

    public void navigateCrossroadsFromATrack() throws InterruptedException {
        // wait for the necessary conditions to get access to crossroads from A track
        crossroadMutex.acquire();

        //move from 8 to 9
        slots[9] = slots[8];
        slots[8] = "[..]";
        activity.addMovedTo(9);

        //move from 9 to 0
        slots[0] = slots[9];
        slots[9] = "[..]";
        activity.addMovedTo(0);

        //move from 0 to 10
        slots[10] = slots[0];
        slots[0] = "[..]";
        activity.addMovedTo(10);

        crossroadMutex.release();

        //move from 10 to 11
        slotSem[11].acquire();
        slots[11] = slots[10];
        slots[10] = "[..]";
        activity.addMovedTo(11);

        slotSem[8].release();
    }

    public void navigateCrossroadsFromBTrack() throws InterruptedException {
        // wait for the necessary conditions to get access to crossroads from A track
        crossroadMutex.acquire();

        //move from 17 to 18
        slots[18] = slots[17];
        slots[17] = "[..]";
        activity.addMovedTo(18);

        //move from 18 to 0
        slots[0] = slots[18];
        slots[18] = "[..]";
        activity.addMovedTo(0);

        //move from 0 to 1
        slots[1] = slots[0];
        slots[0] = "[..]";
        activity.addMovedTo(1);

        crossroadMutex.release();

        //move from 1 to 2
        slotSem[2].acquire();
        slots[2] = slots[1];
        slots[1] = "[..]";
        activity.addMovedTo(2);

        slotSem[17].release();
    }

    public void trainMoveFrom11To14() throws InterruptedException {
        int currentPosition = 11;
        while (currentPosition < 14) {
            //wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        }
    }

    public void trainMove14To17() throws InterruptedException {
        int currentPosition = 14;
        while (currentPosition < 17) {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        }
    }

    public void trainAMoveOffTrack() {
        activity.addMessage("Train " + slots[5] + " is leaving the loop at section 5");
        // move train type A off slot 5
        slots[5] = "[..]";
        slotSem[5].release();
        totalTrainSem.release();
    }

    public void trainBMoveOffTrack() {
        activity.addMessage("Train " + slots[14] + " is leaving the loop at section 14");
        // move train type A off slot 14
        slots[14] = "[..]";
        slotSem[14].release();
        totalTrainSem.release();
    }
}
