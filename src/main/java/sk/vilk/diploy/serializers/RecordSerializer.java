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

    @Override
    public int serialize(OutputBuffer out, Record record) {
        int written = 0;
        UUID uuid = record.getId();
        UUIDSerializer uuidSerializer = new UUIDSerializer();
        written += uuidSerializer.serialize(out, uuid);
        int position = out.skip(8);
        written += 8;

        Header header = record.getHeader();
        written += out.writeSignedVarInt(header.getSize());

        for (SerialType serialType: header.getSerialTypes()) {
            written += out.writeUnsignedVarInt(serialType.getSerialValue());
        }

        for (Attribute<?> attribute: record.getValues()) {
            Serializer serializer = SerializerForClass.get(attribute.getClazz());
            written += serializer.serialize(out, attribute.getValue());
        }

        OutputByteBuffer out2 = new OutputByteBuffer(9);
        out2.writeLong(written);
        record.setNext(written);

        out.insertBytes(out2.toBytes(), position, 8);

        return written;
    }

    @Override
    public Record deserialize(InputBuffer in) {
        Record record = new Record();
        UUIDSerializer uuidSerializer = new UUIDSerializer();
        record.setId(uuidSerializer.deserialize(in));

        long next = in.readLong();
        record.setNext(next);

        int attributeCount = in.readSignedVarInt();
        Header header = new Header(attributeCount);
        for (int i = 0; i < attributeCount; i++) {
            int value = in.readUnsignedVarInt();
            header.addSerialType(value);
        }

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

    @Override
    public int size() {
        return 0;
    }
}
