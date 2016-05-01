package global.struct;

import java.util.LinkedList;

/**
 * Created by Steiner on 01.05.2016.
 */
public class CyclicList<T> extends LinkedList<T> {

    private int ptr = 0;

    public CyclicList(LinkedList<T> ll) {
        for (T value: ll)
            this.add(value);
    }

    public T curr() {
        return this.get(ptr);
    }

    public T next() {
        ++ptr;
        if (ptr == this.size()) ptr = 0;
        return this.get(ptr);
    }

    public T prev() {
        --ptr;
        if (ptr == -1) ptr = this.size()-1;
        return this.get(ptr);
    }
}
