package sk.vilk.diploy.record;

public class Attribute<T> {
    private T value;
    private Class<?> clazz;

    public Attribute(T value) {
        this.value = value;
        this.clazz = value.getClass();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "value=" + value +
                ", clazz=" + clazz +
                '}';
    }

    @Override
    public boolean equals(Object otherAttr) {
        return otherAttr.getClass() == Attribute.class &&
                value.equals(((Attribute<?>) otherAttr).value) &&
                clazz.equals(((Attribute<?>) otherAttr).clazz);
    }
}
