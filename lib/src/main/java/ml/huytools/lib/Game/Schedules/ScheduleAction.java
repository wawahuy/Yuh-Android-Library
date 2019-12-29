package ml.huytools.lib.Game.Schedules;

public class ScheduleAction {
    private enum LOOP { INFINITE, ONE, N };

    private LOOP nLoop;
    private ScheduleCallback callback;
    private int timeout;
    private int interval;
    private int n;

    private boolean hasTimeout;
    private long timeCurrent;
    private long time, dt;
    private int nCurrent;

    enum PositionThread { CURRENT, MAIN }
    private PositionThread schedulePositionThread;

    private ScheduleAction(ScheduleCallback callback){
        this.callback = callback;
        hasTimeout = true;
        interval = 0;
    }

    public static ScheduleAction One(ScheduleCallback callback, int timeout){
        ScheduleAction scheduleAction = new ScheduleAction(callback);
        scheduleAction.timeout = timeout;
        scheduleAction.nLoop = LOOP.ONE;
        return scheduleAction;
    }

    public static ScheduleAction Infinite(ScheduleCallback callback, int interval, int timeout){
        ScheduleAction scheduleAction = new ScheduleAction(callback);
        scheduleAction.timeout = timeout;
        scheduleAction.interval = interval;
        scheduleAction.nLoop = LOOP.INFINITE;
        return scheduleAction;
    }

    public static ScheduleAction N(ScheduleCallback callback, int n, int interval, int timeout){
        ScheduleAction scheduleAction = new ScheduleAction(callback);
        scheduleAction.timeout = timeout;
        scheduleAction.n = n;
        scheduleAction.interval = interval;
        scheduleAction.nLoop = LOOP.N;
        return scheduleAction;
    }

    public void init(PositionThread positionThread){
        timeCurrent = System.currentTimeMillis();
        nCurrent = 0;
        schedulePositionThread = positionThread;
    }

    public PositionThread getSchedulePositionThread() {
        return schedulePositionThread;
    }

    public boolean run() {
        time = System.currentTimeMillis();

        // timeout
        if(hasTimeout){
            if(time - timeCurrent > timeout){
                hasTimeout = false;
            }
            timeCurrent = time;
            return true;
        }

        // main
        dt = time - timeCurrent;
        if(dt >= interval) {
            callback.OnScheduleCallback(dt);

            switch (nLoop){
                case ONE:
                    return false;

                case N:
                    nCurrent++;
                    if(nCurrent > n){
                        return false;
                    }
            }

            timeCurrent = time;
        }

        return true;
    }
}
