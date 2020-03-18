package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class CharSerializer implements Serializer<Character> {
    @Override
    public void serialize(OutputBuffer out, Character value) {
        out.writeChar(value);
    }

    @Override
    public Character deserialize(InputBuffer in) {
        return in.readChar();
    }

    @Override
    public Class type() {
        return Character.class;
    }

    @Override
    public int size() {
        return 1;
    }
}
