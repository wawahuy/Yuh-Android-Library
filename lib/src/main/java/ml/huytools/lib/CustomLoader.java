package ml.huytools.lib;

import android.os.SystemClock;

import java.util.LinkedList;

public abstract class CustomLoader extends Loader<Object> implements Loader.Callback<Object> {

    protected abstract void OnUpdateProgress(int p);
    protected abstract void OnUpdateText(String text);
    protected abstract void OnStartLoad();
    protected abstract void OnCompleteLoad();

    LinkedList<Load> loads;
    int perOnce;
    int perCurrent;
    int pos;
    boolean loop;
    Load loadCurrent;

    public CustomLoader(){
        loads = new LinkedList<>();
        Init(this);
        OnStartLoad();
    }

    @Override
    public synchronized void start() {
        if(loads.size()==0)
            return;

        perOnce = 100/loads.size();
        perCurrent = 0;
        pos = 0;
        loop = true;
        if(pos < loads.size())
            loadCurrent = loads.get(0);

        super.start();
    }

    public void AddLoad(Load load){
        loads.add(load);
    }

    void changeBarUI(final int per){
        handler.post(new Runnable() {
            @Override
            public void run() {
                OnUpdateProgress(per);
            }
        });
    }

    void changeTextUI(final String t){
        handler.post(new Runnable() {
            @Override
            public void run() {
                OnUpdateText(t);
            }
        });
    }

    @Override
    public void OnBackgroundLoad(Loader loader) {
        while (loop){
            if(pos >= loads.size()){
                break;
            }

            switch (loadCurrent.isLoad()){
                case PRE:
                    loadCurrent.start();
                    changeTextUI(loadCurrent.textBeforeLoad);
                    break;

                case POST:
                    if(loadCurrent.isError()){
                        changeTextUI(loadCurrent.textLoadError);
                        perCurrent = perOnce*pos;
                        loadCurrent.start();
                        SystemClock.sleep(2000);
                        changeTextUI(loadCurrent.textBeforeLoad);
                    } else {
                        changeBarUI(perOnce*(pos+1));
                        pos++;
                        if(pos < loads.size())
                            loadCurrent = loads.get(pos);
                    }
                    break;

                case ING:
                    if(perCurrent < perOnce*(pos+1) - 5){
                        changeBarUI(++perCurrent);
                    }
                    break;
            }

            SystemClock.sleep(250);
        }
    }

    @Override
    public void OnChangeLoad(Loader loader, Object... message) {

    }

    @Override
    public void OnFinishLoad(Loader loader) {
        OnCompleteLoad();
    }


    public abstract static class Load<T> implements Runnable  {
        enum LOAD {PRE, ING, POST};
        String textBeforeLoad, textLoadError;
        LinkedList<CBError<T>> errors;
        T o;
        LOAD load;
        Thread t;

        public Load(String textBeforeLoad, String textLoadError) {
            this.textBeforeLoad = textBeforeLoad;
            this.textLoadError = textLoadError;
            errors = new LinkedList<>();
            load = LOAD.PRE;
        }

        protected abstract T OnRun();

        protected void OnAddError(){
            AddErrorNull();
        };


        public synchronized void start() {
            load = LOAD.ING;
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run(){
            OnAddError();
            o = OnRun();
            load = LOAD.POST;
        }

        boolean isError(){
            for(CBError<T> error:errors){
                if(error.error(o))
                    return true;
            }
            return false;
        }

        LOAD isLoad(){
            return load;
        }

        public Load<T> AddError(CBError error){
            errors.add(error);
            return this;
        }

        public Load<T> AddErrorNull(){
            return AddError(new CBError<T>() {
                @Override
                public boolean error(T o) {
                    return o == null;
                }
            });
        }

        public interface CBError<T> {
            boolean error(T o);
        }
    }

}
