package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class FloatSerializer implements Serializer<Float> {
    @Override
    public void serialize(OutputBuffer out, Float value) {
        out.writeFloat(value);
    }

    @Override
    public Float deserialize(InputBuffer in) {
        return in.readFloat();
    }

    @Override
    public Class type() {
        return Float.class;
    }

    @Override
    public int size() {
        return 4;
    }
}
