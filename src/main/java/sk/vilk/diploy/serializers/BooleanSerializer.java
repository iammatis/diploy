package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class BooleanSerializer implements Serializer<Boolean> {
    @Override
    public void serialize(OutputBuffer out, Boolean value) {
        out.writeBoolean(value);
    }

    @Override
    public Boolean deserialize(InputBuffer in) {
        return in.readBoolean();
    }

    @Override
    public Class type() {
        return Boolean.class;
    }

    @Override
    public int size() {
        return 1;
    }
}
