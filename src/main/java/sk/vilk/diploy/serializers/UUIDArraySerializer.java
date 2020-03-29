package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.UUID;

public class UUIDArraySerializer implements Serializer<UUID[]> {

    private static final UUIDSerializer serializer = new UUIDSerializer();

    @Override
    public int serialize(OutputBuffer out, UUID[] array) {
        int size = 0;
        size += out.writeInt(array.length);

        for (UUID uuid: array) {
            size += serializer.serialize(out, uuid);
        }

        return size;
    }

    @Override
    public UUID[] deserialize(InputBuffer in) {
        int length = in.readInt();
        UUID[] array = new UUID[length];
        for (int i = 0; i < length; i++) {
            array[i] = serializer.deserialize(in);
        }
        return array;
    }

    @Override
    public Class<?> type() {
        return UUID[].class;
    }
}
