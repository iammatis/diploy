package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.lang.reflect.Array;

public interface ArraySerializer<T> extends Serializer<T> {
    default void serializeArray(OutputBuffer out, T[] array) {
        out.writeInt(array.length);
        for (T value: array) {
            serialize(out, value);
        }
    }

    default T[] deserializeArray(InputBuffer in) {
//        int length = in.readInt();
//        T[] array = (T[]) Array.newInstance(T, length);
//        for (int i = 0; i < length ; i++) {
//            array[i] = deserialize(in);
//        }
//        return array;
        return null;
    }
}
