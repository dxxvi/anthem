package home;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyQueue<T> {
    private final Queue<T> queue = new LinkedList<>();

    synchronized public void add(T t) {
        queue.add(t);
    }

    synchronized public List<T> clear() {
        List<T> list = new LinkedList<>(queue);
        queue.clear();
        return list;
    }
}
