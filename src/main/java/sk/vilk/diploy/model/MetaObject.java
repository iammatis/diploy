package sk.vilk.diploy.model;

import java.io.Serializable;

public class MetaObject implements Serializable {

    private Object object;
    private Object id;
    private long from;
    private int length;

    public MetaObject(Object object, Object id, long from, int length) {
        this.object = object;
        this.id = id;
        this.from = from;
        this.length = length;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
