package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class StringSerializer implements Serializer<String> {
    @Override
    public void serialize(OutputBuffer out, String value) {
        out.writeString(value);
    }

    @Override
    public String deserialize(InputBuffer in) {
        return in.readString();
    }

    @Override
    public Class type() {
        return String.class;
    }
}
