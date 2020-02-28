package com.train;

import java.util.concurrent.Semaphore;

public class TrainTrack {
    //create track with slots
    private String[] slots = {"[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]",
            "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]", "[..]"};

    // declare array to hold the binary Semaphores for access to track slots
    private final static Semaphore[] slotSem = new Semaphore[19];

    //semaphore to limit number of trains
    private final static Semaphore aCountSem = new Semaphore(4);
    private final static Semaphore bCountSem = new Semaphore(4);

    // create variable to record activity
    Activity activity;

    public TrainTrack() {
        // record the train activity
        activity = new Activity(slots);
        for (int i = 0; i <= 18; i++) {
            // create the array of slotSems and give them all 1 free permit
            slotSem[i] = new Semaphore(1);
        }
    }

    public void trainAMoveOnToTrack(String trainName) throws InterruptedException {
        CDS.idleQuietly((int) (Math.random() * 100));
        // limit number of A trains on track to avoid deadlock
        aCountSem.acquire();
        // wait for slot 4 & 5 to be free before entering track
        slotSem[4].acquire();
        slotSem[5].acquire();
        slots[5] = "[" + trainName + "]";
        activity.addMovedTo(5);
    }

    public void trainBMoveOnToTrack(String trainName) throws InterruptedException {
        CDS.idleQuietly((int) (Math.random() * 100));
        // limit number of B trains on track to avoid deadlock
        bCountSem.acquire();
        // wait for slot 13 & 14 to be free before entering track
        slotSem[13].acquire();
        slotSem[14].acquire();
        slots[14] = "[" + trainName + "]";
        activity.addMovedTo(14);
    }

    public void trainMove5To8() throws InterruptedException {
        slotSem[4].release();
        CDS.idleQuietly((int) (Math.random() * 10));
        int currentPosition = 5;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        } while (currentPosition < 8);
        CDS.idleQuietly((int) (Math.random() * 100));
    }

    public void navigateCrossroadsFromATrack() throws InterruptedException {
        //release last slot for train to queue for crossroad
        slotSem[8].release();
        // wait for the necessary conditions to get access to crossroads from A track

        //move from 8 to 9
        slotSem[9].acquire();
        slots[9] = slots[8];
        slots[8] = "[..]";
        activity.addMovedTo(9);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[9].release();

        //move from 9 to 0
        slotSem[0].acquire();
        slots[0] = slots[9];
        slots[9] = "[..]";
        activity.addMovedTo(0);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[0].release();

        //move from 0 to 10
        slotSem[10].acquire();
        slots[10] = slots[0];
        slots[0] = "[..]";
        activity.addMovedTo(10);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[10].release();

        //move from 10 to 11
        slotSem[11].acquire();
        slots[11] = slots[10];
        slots[10] = "[..]";
        activity.addMovedTo(11);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[11].release();
    }

    public void navigateCrossroadsFromBTrack() throws InterruptedException {
        //release last slot for train to queue for crossroad
        slotSem[17].release();
        // wait for the necessary conditions to get access to crossroads from A track

        //move from 17 to 18
        slotSem[18].acquire();
        slots[18] = slots[17];
        slots[17] = "[..]";
        activity.addMovedTo(18);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[18].release();

        //move from 18 to 0
        slotSem[0].acquire();
        slots[0] = slots[18];
        slots[18] = "[..]";
        activity.addMovedTo(0);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[0].release();

        //move from 0 to 1
        slotSem[1].acquire();
        slots[1] = slots[0];
        slots[0] = "[..]";
        activity.addMovedTo(1);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[1].release();

        //move from 1 to 2
        slotSem[2].acquire();
        slots[2] = slots[1];
        slots[1] = "[..]";
        activity.addMovedTo(2);
        CDS.idleQuietly((int) (Math.random() * 10));
        slotSem[2].release();
    }

    public void trainMoveFrom2To5() throws InterruptedException {
        CDS.idleQuietly((int) (Math.random() * 100));
        int currentPosition = 2;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        } while (currentPosition < 5);
        CDS.idleQuietly((int) (Math.random() * 100));
    }

    public void trainMoveFrom11To14() throws InterruptedException {
        CDS.idleQuietly((int) (Math.random() * 100));
        int currentPosition = 11;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        } while (currentPosition < 14);
        CDS.idleQuietly((int) (Math.random() * 100));
    }

    public void trainMove14To17() throws InterruptedException {
        slotSem[13].release();
        CDS.idleQuietly((int) (Math.random() * 10));
        int currentPosition = 14;
        do {
            // wait until the position ahead is empty and then move into it
            slotSem[currentPosition + 1].acquire();  // wait for the slot ahead to be free
            slots[currentPosition + 1] = slots[currentPosition]; // move train forward one position
            slots[currentPosition] = "[..]"; // clear the slot the train vacated
            activity.addMovedTo(currentPosition + 1); // record the train activity
            slotSem[currentPosition].release(); // signal slot you are leaving
            currentPosition++;
        } while (currentPosition < 17);
        CDS.idleQuietly((int) (Math.random() * 100));
    }

    public void trainAMoveOffTrack() {
        CDS.idleQuietly((int) (Math.random() * 10));
        activity.addMessage("Train " + slots[5] + " is leaving the loop at section 5");
        // move train type A off slot 5
        slots[5] = "[..]";
        slotSem[5].release();

        CDS.idleQuietly((int) (Math.random() * 10));
    }

    public void trainBMoveOffTrack() {
        CDS.idleQuietly((int) (Math.random() * 10));
        activity.addMessage("Train " + slots[14] + " is leaving the loop at section 14");
        // move train type A off slot 14
        slots[14] = "[..]";
        slotSem[14].release();

        CDS.idleQuietly((int) (Math.random() * 10));
    }
}
