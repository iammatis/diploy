package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.lang.reflect.Array;
import java.util.UUID;

public class UUIDArraySerializer implements Serializer<UUID[]> {

    private static UUIDSerializer serializer = new UUIDSerializer();

    @Override
    public void serialize(OutputBuffer out, UUID[] array) {
        out.writeInt(array.length);
        for (UUID uuid: array) {
            serializer.serialize(out, uuid);
        }
    }

    @Override
    public UUID[] deserialize(InputBuffer in) {
        int legnth = in.readInt();
        UUID[] array = (UUID[]) Array.newInstance(UUID.class, legnth);
        for (int i = 0; i < legnth; i++) {
            array[i] = serializer.deserialize(in);
        }
        return array;
    }

    @Override
    public Class type() {
        return UUID[].class;
    }
}
