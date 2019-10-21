package sk.vilk.diploy.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/*
    Wrapper class for byte buffer, extended with some custom methods
 */
public abstract class AbstractBuffer implements MetaConstants {

    ByteBuffer byteBuffer;

    AbstractBuffer(int capacity) {
        byteBuffer = ByteBuffer.allocate(capacity);
    }

    AbstractBuffer(byte[] bytes) {
        byteBuffer = ByteBuffer.wrap(bytes);
    }

    public void putAnnotation(Annotation annotation, Field field) {
        switch (annotation.annotationType().getSimpleName()) {
            case "OneToOne":
                putOneToOne(annotation, field);
                break;
            case "OneToMany":
                putOneToMany(annotation, field);
                break;
            case "ManyToMany":
                putManyToMany(annotation, field);
                break;
        }
    }

    public abstract void putOneToOne(Annotation annotation, Field field);

    public abstract void putOneToMany(Annotation annotation, Field field);

    public abstract void putManyToMany(Annotation annotation, Field field);

    public abstract void putField(Field field);

    public abstract void putField(Annotation annotation, Field field);

    public void putByte(byte value) {
        byteBuffer.put(value);
    }

    public void putString(String string) {
        byteBuffer.put(string.getBytes(Charset.forName(CHARSET)));
    }

    public void putInt(Integer integer) {
        byteBuffer.putInt(integer);
    }

    public Integer getInt() {
        return byteBuffer.getInt();
    }

    public byte get() {
        return byteBuffer.get();
    }

    public ByteBuffer get(byte[] dst, int offset, int length) {
        return byteBuffer.get(dst, offset, length);
    }

    public byte[] array() {
        return byteBuffer.array();
    }

    public boolean hasRemaining() {
        return byteBuffer.hasRemaining();
    }
}
