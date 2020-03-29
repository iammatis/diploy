package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class LongSerializer implements Serializer<Long> {
    @Override
    public int serialize(OutputBuffer out, Long value) {
        return out.writeSignedVarLong(value);
    }

    @Override
    public Long deserialize(InputBuffer in) {
        return in.readSignedVarLong();
    }

    @Override
    public Class<Long> type() {
        return Long.class;
    }

    @Override
    public int size() {
        return 8;
    }
}
