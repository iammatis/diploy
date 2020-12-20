package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.UUID;

public class OneToManySerializer implements Serializer<UUID> {
    @Override
    public int serialize(OutputBuffer out, UUID value) {
        return 1;
    }

    @Override
    public UUID deserialize(InputBuffer in) {
        return null;
    }

    @Override
    public Class type() {
        return null;
    }
}
