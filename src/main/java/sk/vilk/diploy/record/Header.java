package sk.vilk.diploy.record;

import java.util.*;

public class Header {
    /**
     * Attribute count
     */
    private int size;
    private List<SerialType> serialTypes;

    private static HashMap<Class<?>, SerialType> SERIALTYPE_FOR_CLASS = new HashMap<>();
    private static HashMap<Integer, SerialType> SERIALTYPE_FOR_VALUE = new HashMap<>();

    static {
        SERIALTYPE_FOR_CLASS.put(Short.class, SerialType.SHORT);
        SERIALTYPE_FOR_CLASS.put(Integer.class, SerialType.INTEGER);
        SERIALTYPE_FOR_CLASS.put(Long.class, SerialType.LONG);

        SERIALTYPE_FOR_CLASS.put(String.class, SerialType.STRING);



        SERIALTYPE_FOR_VALUE.put(1, SerialType.SHORT);
        SERIALTYPE_FOR_VALUE.put(2, SerialType.INTEGER);
        SERIALTYPE_FOR_VALUE.put(3, SerialType.LONG);
        SERIALTYPE_FOR_VALUE.put(4, SerialType.DOUBLE);
        SERIALTYPE_FOR_VALUE.put(5, SerialType.FLOAT);

        SERIALTYPE_FOR_VALUE.put(6, SerialType.BOOLEAN);

        SERIALTYPE_FOR_VALUE.put(7, SerialType.CHARACTER);
        SERIALTYPE_FOR_VALUE.put(8, SerialType.STRING);

        SERIALTYPE_FOR_VALUE.put(9, SerialType.DATE);
        SERIALTYPE_FOR_VALUE.put(10, SerialType.UUID);
    }

    public Header(Integer size) {
        this(size, new ArrayList<>());
    }

    public Header(Integer size, List<SerialType> serialTypes) {
        this.size = size;
        this.serialTypes = serialTypes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<SerialType> getSerialTypes() {
        return serialTypes;
    }

    public void setSerialTypes(List<SerialType> serialTypes) {
        this.serialTypes = serialTypes;
    }

    public void addSerialType(SerialType serialType) {
        serialTypes.add(serialType);
    }

    public void addSerialType(Class<?> clazz) {
        serialTypes.add(SERIALTYPE_FOR_CLASS.get(clazz));
    }

    public void addSerialType(int value) {
        serialTypes.add(SERIALTYPE_FOR_VALUE.get(value));
    }

    @Override
    public String toString() {
        return "Header{" +
                "count=" + size +
                ", serialTypes=" + serialTypes +
                '}';
    }

    @Override
    public boolean equals(Object otherHeader) {
        return otherHeader.getClass() == Header.class &&
                this.size == (((Header) otherHeader).size) &&
                this.serialTypes.equals(((Header) otherHeader).serialTypes);
    }
}
