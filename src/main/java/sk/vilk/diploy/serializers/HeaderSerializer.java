package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;
import sk.vilk.diploy.record.Header;

import java.util.List;

public class HeaderSerializer implements Serializer<Header> {
    @Override
    public void serialize(OutputBuffer out, Header header) {
        out.writeInt(header.getSize());
        for (Integer type: header.getAttributeTypes()) {
            out.writeInt(type);
        }
    }

    @Override
    public Header deserialize(InputBuffer in) {
        Integer size = 1;
        List<Integer> attributeTypes = List.of();
        return new Header(size, attributeTypes);
    }

    @Override
    public Class type() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
