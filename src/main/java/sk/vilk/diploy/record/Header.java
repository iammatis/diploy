package sk.vilk.diploy.record;

import java.util.*;

public class Header {
    /**
     * Attribute count
     */
    private Integer size;
    private List<SerialType> serialTypes;

    private static HashMap<Class<?>, SerialType> SERIALTYPE_FOR_CLASS = new HashMap<>();

    static {
        SERIALTYPE_FOR_CLASS.put(Short.class, SerialType.SHORT);
        SERIALTYPE_FOR_CLASS.put(Integer.class, SerialType.INTEGER);
        SERIALTYPE_FOR_CLASS.put(Long.class, SerialType.LONG);

        SERIALTYPE_FOR_CLASS.put(String.class, SerialType.STRING);
    }

    public Header(Integer size) {
        this(size, new ArrayList<>());
    }

    public Header(Integer size, List<SerialType> serialTypes) {
        this.size = size;
        this.serialTypes = serialTypes;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
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
        if (value == 1) {
            serialTypes.add(SerialType.SHORT);
        } else if (value == 2) {
            serialTypes.add(SerialType.INTEGER);
        } else if (value == 3) {
            serialTypes.add(SerialType.LONG);
        } else if (value == 4) {
            serialTypes.add(SerialType.DOUBLE);
        } else if (value == 5) {
            serialTypes.add(SerialType.FLOAT);
        } else if (value == 6) {
            serialTypes.add(SerialType.BOOLEAN);
        } else if (value == 7) {
            serialTypes.add(SerialType.CHARACTER);
        } else if (value == 8) {
            serialTypes.add(SerialType.STRING);
        } else if (value == 9) {
            serialTypes.add(SerialType.DATE);
        } else if (value == 10) {
            serialTypes.add(SerialType.UUID);
        }
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
                this.size.equals(((Header) otherHeader).size) &&
                this.serialTypes.equals(((Header) otherHeader).serialTypes);
    }
}
