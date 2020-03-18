package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public interface Serializer<T> {
    public void serialize(OutputBuffer out, T value);

    public T deserialize(InputBuffer in);

    public Class type();

    // TODO: Useless?
    default int size() {
        return -1;
    };
}
