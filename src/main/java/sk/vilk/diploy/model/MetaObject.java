package sk.vilk.diploy.model;

public class MetaObject {

    private Object object;
    private long from;
    private int length;

    public MetaObject(Object object, long from, int length) {
        this.object = object;
        this.from = from;
        this.length = length;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
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
