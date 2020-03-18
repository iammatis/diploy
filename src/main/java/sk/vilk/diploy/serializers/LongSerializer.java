package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class LongSerializer implements Serializer<Long> {
    @Override
    public void serialize(OutputBuffer out, Long value) {
        System.out.println("value: " + value);
        out.writeLong(value);
    }

    @Override
    public Long deserialize(InputBuffer in) {
        return in.readLong();
    }

    @Override
    public Class type() {
        return Long.class;
    }

    @Override
    public int size() {
        return 8;
    }
}
