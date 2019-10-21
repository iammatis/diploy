package sk.vilk.diploy.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidSerializer {

    public static byte[] encode(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    public static byte[] encode(String uuidString) {
        return encode(UUID.fromString(uuidString));
    }

    public static UUID decode(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();

        return new UUID(high, low);
    }

}
