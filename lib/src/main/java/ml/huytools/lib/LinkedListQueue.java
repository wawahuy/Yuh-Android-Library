package ml.huytools.lib;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListQueue<T> extends LinkedList<T> {
    List<Runnable> listAction;
    boolean hasUpdate;

    public LinkedListQueue(){
        listAction = Collections.synchronizedList(new LinkedList<Runnable>());
    }

    public void addQueue(final T t){
        hasUpdate = true;
        synchronized (listAction){
            listAction.add(new Runnable() {
                @Override
                public void run() {
                    add(t);
                }
            });
        }
    }

    public void removeQueue(final T t){
        hasUpdate = true;
        synchronized (listAction){
            listAction.add(new Runnable() {
                @Override
                public void run() {
                    remove(t);
                }
            });
        }
    }

    public void addQueue(final int index, final T t){
        hasUpdate = true;
        synchronized (listAction){
            listAction.add(new Runnable() {
                @Override
                public void run() {
                    add(index, t);
                }
            });
        }
    }

    public void sortQueue(final Comparator<? super T> c){
        hasUpdate = true;
        synchronized (listAction){
            listAction.add(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(LinkedListQueue.this, c);
                }
            });
        }
    }

    public void updateQueue(){
        if (!hasUpdate) {
            return;
        }

        synchronized (listAction) {
            for(Runnable runnable:listAction){
                runnable.run();
            }
            listAction.clear();
        }

        hasUpdate = false;
    }
}
