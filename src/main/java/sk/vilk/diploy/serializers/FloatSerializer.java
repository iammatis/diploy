package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class FloatSerializer implements Serializer<Float> {
    @Override
    public int serialize(OutputBuffer out, Float value) {
        return out.writeFloat(value);
    }

    @Override
    public Float deserialize(InputBuffer in) {
        return in.readFloat();
    }

    @Override
    public Class<Float> type() {
        return Float.class;
    }

    @Override
    public int size() {
        return 4;
    }
}
