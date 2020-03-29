package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.ArrayList;
import java.util.List;

public class ListSerializer<T> implements Serializer<List<T>> {

    private Serializer<T> serializer;

    public ListSerializer() {
        this(Object.class);
    }

    public ListSerializer(Class<?> clazz) {
        this.serializer = (Serializer<T>) SerializerForClass.get(clazz);
    }

    @Override
    public int serialize(OutputBuffer out, List<T> list) {
        int written = out.writeUnsignedVarInt(list.size());
        for (T val: list) {
            written += serializer.serialize(out, val);
        }

        return written;
    }

    @Override
    public List<T> deserialize(InputBuffer in) {
        int size = in.readUnsignedVarInt();
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(serializer.deserialize(in));
        }

        return list;
    }

    @Override
    public Class<?> type() {
        return null;
    }
}
