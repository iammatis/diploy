package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;
import sk.vilk.diploy.record.Header;
import sk.vilk.diploy.record.SerialType;

public class HeaderSerializer implements Serializer<Header> {
    @Override
    public int serialize(OutputBuffer out, Header header) {
        int written = 0;

        written += out.writeUnsignedVarInt(header.getSize());

        for (SerialType type: header.getSerialTypes()) {
            written += out.writeUnsignedVarInt(type.getSerialValue());
        }

        return written;
    }

    @Override
    public Header deserialize(InputBuffer in) {
        int attributeCount = in.readUnsignedVarInt();
        Header header = new Header(attributeCount);

        for (int i = 0; i < attributeCount; i++) {
            int value = in.readUnsignedVarInt();
            header.addSerialType(value);
        }

        return header;
    }

    @Override
    public Class<Header> type() {
        return Header.class;
    }

    @Override
    public int size() {
        return 0;
    }
}
