package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.record.Attribute;
import sk.vilk.diploy.record.Header;
import sk.vilk.diploy.record.Record;
import sk.vilk.diploy.record.SerialType;

import java.util.UUID;

public class RecordSerializer implements Serializer<Record> {

    private static final HeaderSerializer headerSerializer = new HeaderSerializer();
    private static final UUIDSerializer uuidSerializer = new UUIDSerializer();

    @Override
    public int serialize(OutputBuffer out, Record record) {
        int written = 0;

        UUID uuid = record.getId();
        written += uuidSerializer.serialize(out, uuid);

        int position = out.skip(4);
        written += 4;

        Header header = record.getHeader();
        written += headerSerializer.serialize(out, header);

        for (Attribute<?> attribute: record.getValues()) {
            Serializer serializer = SerializerForClass.get(attribute.getClazz());
            written += serializer.serialize(out, attribute.getValue());
        }

        OutputByteBuffer out2 = new OutputByteBuffer(5);
        out2.writeInt(written);
        record.setNext(written);

        out.insertBytes(out2.toBytes(), position, 4);

        return written;
    }

    @Override
    public Record deserialize(InputBuffer in) {
        Record record = new Record();
        record.setId(uuidSerializer.deserialize(in));

        int next = in.readInt();
        record.setNext(next);

        Header header = headerSerializer.deserialize(in);
        record.setHeader(header);

        for (SerialType serialType: header.getSerialTypes()) {
            Serializer serializer = SerializerForClass.get(serialType.getClazz());
            Object deserialized = serializer.deserialize(in);
            record.addValue(new Attribute<>(deserialized));
        }

        return record;
    }

    @Override
    public Class<Record> type() {
        return Record.class;
    }
}
