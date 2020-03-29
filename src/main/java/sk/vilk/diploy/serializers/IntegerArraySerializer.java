package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class IntegerArraySerializer implements Serializer<Integer[]> {
    private static final IntegerSerializer serializer = new IntegerSerializer();

    @Override
    public int serialize(OutputBuffer out, Integer[] value) {
        int size = 0;
        size += out.writeUnsignedVarInt(value.length);

        for (Integer shit: value) {
            size += serializer.serialize(out, shit);
        }
        return size;
    }

    @Override
    public Integer[] deserialize(InputBuffer in) {
        int length = in.readUnsignedVarInt();
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = in.readSignedVarInt();
        }

        return array;
    }

    @Override
    public Class<?> type() {
        return Integer[].class;
    }
}
