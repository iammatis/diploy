package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

public class CharSerializer implements Serializer<Character> {
    @Override
    public int serialize(OutputBuffer out, Character value) {
        return out.writeChar(value);
    }

    @Override
    public Character deserialize(InputBuffer in) {
        return in.readChar();
    }

    @Override
    public Class<Character> type() {
        return Character.class;
    }

    @Override
    public int size() {
        return 1;
    }
}
