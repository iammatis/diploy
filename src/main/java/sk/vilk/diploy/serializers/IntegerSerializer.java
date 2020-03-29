package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class IntegerSerializer implements Serializer<Integer> {
    @Override
    public int serialize(OutputBuffer out, Integer value) {
        return out.writeSignedVarInt(value);
    }

    @Override
    public Integer deserialize(InputBuffer in) {
        return in.readSignedVarInt();
    }

    @Override
    public Class<Integer> type() {
        return Integer.class;
    }

    @Override
    public int size() {
        return 4;
    }
}
