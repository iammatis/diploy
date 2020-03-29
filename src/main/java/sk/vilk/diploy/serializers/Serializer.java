package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public interface Serializer<T> {
    int serialize(OutputBuffer out, T value);

    T deserialize(InputBuffer in);

    Class<?> type();

    // TODO: Useless?
    default int size() {
        return -1;
    };
}
