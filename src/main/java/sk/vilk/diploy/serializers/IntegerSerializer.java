package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class IntegerSerializer implements Serializer<Integer> {
    @Override
    public void serialize(OutputBuffer out, Integer value) {
        out.writeInt(value);
    }

    @Override
    public Integer deserialize(InputBuffer in) {
        return in.readInt();
    }

    @Override
    public Class type() {
        return Integer.class;
    }

    @Override
    public int size() {
        return 4;
    }
}
