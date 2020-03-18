package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.Date;

public class DateSerializer implements Serializer<Date> {
    @Override
    public void serialize(OutputBuffer out, Date value) {
        out.writeLong(value.getTime());
    }

    @Override
    public Date deserialize(InputBuffer in) {
        return new Date(in.readLong());
    }

    @Override
    public Class type() {
        return Date.class;
    }
}
