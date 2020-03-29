package sk.vilk.diploy.serializers;

import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.OutputBuffer;

import java.util.Date;

public class DateSerializer implements Serializer<Date> {
    @Override
    public int serialize(OutputBuffer out, Date value) {
        return out.writeUnsignedVarLong(value.getTime());
    }

    @Override
    public Date deserialize(InputBuffer in) {
        return new Date(in.readUnsignedVarLong());
    }

    @Override
    public Class<Date> type() {
        return Date.class;
    }
}
