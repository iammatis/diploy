package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class OneToOneSerializer implements Serializer<Object> {
    @Override
    public int serialize(OutputBuffer out, Object value) {
        return 0;
    }

    @Override
    public Object deserialize(InputBuffer in) {
        return null;
    }

    @Override
    public Class<?> type() {
        return null;
    }
}
