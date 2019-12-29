package ml.huytools.lib.Game;

import android.os.SystemClock;

import ml.huytools.lib.Game.Schedules.Scheduler;
import ml.huytools.lib.LinkedListQueue;

public class GameDirector extends Thread {
    private static final GameDirector ourInstance = new GameDirector();

    public static GameDirector getInstance() {
        return ourInstance;
    }

    private LinkedListQueue<Renderer> renders;
    private int framePerSeconds;
    private int sleepOfOnFrame;
    private Scheduler scheduler;

    private GameDirector() {
        renders = new LinkedListQueue<>();
        scheduler = new Scheduler();
        setFramePerSecondsMax(60);
        this.setName("Game Director");
        start();
    }

    public void registration(Renderer renderer){
        setFramePerSecondsMax(framePerSeconds);
        //synchronized (renders){
        renders.addQueue(renderer);
        //}
    }

    public void cancelRegistration(Renderer renderer){
        if(renders.size() <= 1){
            sleepOfOnFrame = 100;
        }
        // synchronized (renders) {
        renders.removeQueue(renderer);
        //}
    }

    public void setFramePerSecondsMax(int frame){
        framePerSeconds = frame;
        sleepOfOnFrame = 1000/framePerSeconds;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void run() {
        long time;

        while (true){
            time = System.currentTimeMillis();
            /// Logic
            for (Renderer renderer : renders) {
                renderer.update();
            }

            /// Render
            for (Renderer renderer : renders) {
                renderer.render();
            }

            /// Scheduler Logic
            scheduler.update();

            /// Update linkedList
            renders.updateQueue();

            /// Compute time sleep
            time = System.currentTimeMillis() - time;
            SystemClock.sleep(time < sleepOfOnFrame ? sleepOfOnFrame - time : 1);
        }
    }

}
