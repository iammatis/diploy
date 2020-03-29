package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class DoubleSerializer implements Serializer<Double> {
    @Override
    public int serialize(OutputBuffer out, Double value) {
        return out.writeDouble(value);
    }

    @Override
    public Double deserialize(InputBuffer in) {
        return in.readDouble();
    }

    @Override
    public Class<Double> type() {
        return Double.class;
    }

    @Override
    public int size() {
        return 8;
    }
}
