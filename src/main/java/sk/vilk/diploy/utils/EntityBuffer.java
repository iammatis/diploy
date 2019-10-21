package sk.vilk.diploy.utils;

import sk.vilk.diploy.AnnotationManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class EntityBuffer extends AbstractBuffer {

    public EntityBuffer(int capacity) {
        super(capacity);
    }

    public EntityBuffer(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void putField(Field field) {

    }

    @Override
    public void putField(Annotation annotation, Field field) {
        putAnnotation(annotation, field);
    }

    @Override
    public void putOneToOne(Annotation annotation, Field field) {

    }

    @Override
    public void putOneToMany(Annotation annotation, Field field) {

    }

    @Override
    public void putManyToMany(Annotation annotation, Field field) {

    }

    public void putBytes(byte[] bytes) {
        byteBuffer.put(bytes);
    }
}
