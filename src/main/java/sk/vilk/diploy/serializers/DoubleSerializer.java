package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class DoubleSerializer implements Serializer<Double> {
    @Override
    public void serialize(OutputBuffer out, Double value) {
        System.out.println("value: " + value);
        out.writeDouble(value);
    }

    @Override
    public Double deserialize(InputBuffer in) {
        return in.readDouble();
    }

    @Override
    public Class type() {
        return Double.class;
    }

    @Override
    public int size() {
        return 8;
    }
}
