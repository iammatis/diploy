package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.UUID;

public class UUIDSerializer implements Serializer<UUID> {
    @Override
    public int serialize(OutputBuffer out, UUID value) {
        out.writeLong(value.getMostSignificantBits());
        out.writeLong(value.getLeastSignificantBits());

        return 16;
    }

    @Override
    public UUID deserialize(InputBuffer in) {
        return new UUID(in.readLong(), in.readLong());
    }

    @Override
    public Class<UUID> type() {
        return UUID.class;
    }

    @Override
    public int size() {
        return 16;
    }
}
