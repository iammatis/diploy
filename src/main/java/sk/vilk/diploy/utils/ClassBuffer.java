package sk.vilk.diploy.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ClassBuffer extends AbstractBuffer {

    public ClassBuffer(int capacity) {
        super(capacity);
    }

    public ClassBuffer(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void putField(Field field) {
        switch (field.getType().getSimpleName()) {
            case "Integer":
                byteBuffer.put(BYTE_VALUE_INTEGER);
                break;
            case "String":
                byteBuffer.put(BYTE_VALUE_STRING);
                break;
        }

        String fieldName = field.getName();
        byteBuffer.put((byte) fieldName.length());
        putString(field.getName());
    }

    @Override
    public void putField(Annotation annotation, Field field) {
        putAnnotation(annotation, null);

        String fieldName = field.getName();
        byteBuffer.put((byte) fieldName.length());
        putString(fieldName);
    }

    @Override
    public void putOneToOne(Annotation annotation, Field field) {
        byteBuffer.put(ONE_TO_ONE);
    }

    @Override
    public void putOneToMany(Annotation annotation, Field field) {
        byteBuffer.put(ONE_TO_MANY);
    }

    @Override
    public void putManyToMany(Annotation annotation, Field field) {
        byteBuffer.put(MANY_TO_MANY);
    }

}
