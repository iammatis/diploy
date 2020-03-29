package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class ShortSerializer implements Serializer<Short> {
    @Override
    public int serialize(OutputBuffer out, Short value) {
        return out.writeShort(value);
    }

    @Override
    public Short deserialize(InputBuffer in) {
        return in.readShort();
    }

    @Override
    public Class type() {
        return Short.class;
    }

    @Override
    public int size() {
        return 2;
    }
}
