package sk.vilk.diploy.meta;

import java.io.Serializable;

public class MetaObject implements Serializable {

    // UUID of the entity
    private String id;
    // Position in file main file where the entity bytes start
    private long from;
    // Length of entity's byte array
    private int length;

    public MetaObject(String id, long from, int length) {
        this.id = id;
        this.from = from;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "MetaObject{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", length=" + length +
                '}';
    }
}
