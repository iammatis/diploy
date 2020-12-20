package sk.vilk.diploy.serializers;

import sk.vilk.diploy.record.Relation;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class RelationSerializer<T> implements Serializer<Relation<T>> {

    private Serializer<T> serializer;

    public RelationSerializer(Serializer<T> serializer) {
//        this.serializer = (Serializer<T>) SerializerForClass.get(clazz);
    }

    @Override
    public int serialize(OutputBuffer out, Relation<T> value) {
        int written = 0;
        written += out.writeUnsignedVarInt(value.getSerialType().getSerialValue());

        written += out.writeString(value.getClazz().getName());



        return written;
    }

    @Override
    public Relation deserialize(InputBuffer in) {
        return null;
    }

    @Override
    public Class<?> type() {
        return null;
    }
}
