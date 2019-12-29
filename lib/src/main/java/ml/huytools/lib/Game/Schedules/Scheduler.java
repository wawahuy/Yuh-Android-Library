package ml.huytools.lib.Game.Schedules;

import android.os.SystemClock;

import ml.huytools.lib.Game.Scenes.Scene;
import ml.huytools.lib.LinkedListQueue;

public class Scheduler extends Thread {
    private static int id = 0;
    private Scene scene;
    private LinkedListQueue<ScheduleAction> listAction;
    private LinkedListQueue<ScheduleAction> listActionMainThread;

    public Scheduler(){
        this.scene = null;
        initScheduleCBInit();
        start();
    }

    public Scheduler(Scene scene){
        this.scene = scene;
        initScheduleCBInit();
        setName("Scheduler " + id++);
        start();
    }

    @Override
    public void run() {
        // -------- Thread N ---------
        while (true){
            for (ScheduleAction scheduleAction : listAction) {
                if (!scheduleAction.run()) {
                    listAction.removeQueue(scheduleAction);
                }
            }
            listAction.updateQueue();
            SystemClock.sleep(1);
        }
    }

    public boolean update(){
        // ------- Main Thread -----
        for (ScheduleAction scheduleAction : listActionMainThread) {
            if (!scheduleAction.run()) {
                listActionMainThread.removeQueue(scheduleAction);
            }
        }
        listActionMainThread.updateQueue();
        return false;
    }

    public void schedule(ScheduleAction scheduleAction){
        listAction.addQueue(scheduleAction);
        scheduleAction.init(ScheduleAction.PositionThread.CURRENT);
    }

    public void scheduleOnThreadGame(ScheduleAction scheduleAction){
        listActionMainThread.addQueue(scheduleAction);
        scheduleAction.init(ScheduleAction.PositionThread.MAIN);
    }

    public void remove(ScheduleAction scheduleAction){
        switch (scheduleAction.getSchedulePositionThread()){
            case MAIN:
                listActionMainThread.removeQueue(scheduleAction);
                break;

            case CURRENT:
                listAction.removeQueue(scheduleAction);
                break;
        }
    }

    public void initScheduleCBInit(){
        listAction = new LinkedListQueue<>();
        listActionMainThread = new LinkedListQueue<>();
    }

}
