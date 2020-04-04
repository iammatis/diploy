package sk.vilk.diploy.buffer;

public class InputByteBuffer implements InputBuffer {

    private int position;
    private int size;
    private byte[] bytes;

    public InputByteBuffer(byte[] bytes) {
        this(0, bytes);
    }

    public InputByteBuffer(int position, byte[] bytes) {
        this.position = position;
        this.bytes = bytes;
    }

    public void reset(byte[] newBytes) {
        this.position = 0;
        this.bytes = newBytes;
    }

    @Override
    public byte readByte() {
        return bytes[position++];
    }

    @Override
    public boolean readBoolean() {
        return bytes[position++] == 1;
    }

    @Override
    public short readShort() {
        return (short) ((bytes[position++] << 8) | (bytes[position++] & 0xff));
    }

    @Override
    public int readInt() {
        return ((((int)bytes[position++]) << 24) |
                (((int)bytes[position++] & 0xFF) << 16) |
                (((int)bytes[position++] & 0xFF) <<  8) |
                (((int)bytes[position++] & 0xFF)));
    }

    @Override
    public long readLong() {
        return ((((long)bytes[position++]) << 56) |
                (((long)bytes[position++] & 0xFF) << 48) |
                (((long)bytes[position++] & 0xFF) << 40) |
                (((long)bytes[position++] & 0xFF) << 32) |
                (((long)bytes[position++] & 0xFF) << 24) |
                (((long)bytes[position++] & 0xFF) << 16) |
                (((long)bytes[position++] & 0xFF) <<  8) |
                (((long)bytes[position++] & 0xFF)));
    }

    @Override
    public double readDouble() {
        return Double.longBitsToDouble(readSignedVarLong());
    }

    @Override
    public float readFloat() {
        return Float.intBitsToFloat(readSignedVarInt());
    }

    public char readChar() {
        return (char) (((bytes[position++] & 0xff) << 8) | (bytes[position++] & 0xff));
    }

    @Override
    public String readString() {
        int length = readUnsignedVarInt();
        char[] chars = new char[length];

        for (int i = 0; i < length; i++) {
            chars[i] = (char) readByte();
        }
        return new String(chars);
    }

    public int readSignedVarInt() {
        int raw = readUnsignedVarInt();
        int temp = (((raw << 31) >> 31) ^ raw) >> 1;
        return temp ^ (raw & (1 << 31));
    }

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;
        while (((b = readByte()) & 0x80) != 0) {
            value |= (b & 0x7F) << i;
            i += 7;
            if (i > 35) {
                throw new IllegalArgumentException("Variable length quantity is too long");
            }
        }
        return value | (b << i);
    }

    public long readSignedVarLong() {
        long raw = readUnsignedVarLong();
        long temp = (((raw << 63) >> 63) ^ raw) >> 1;
        return temp ^ (raw & (1L << 63));
    }

    public long readUnsignedVarLong() {
        long value = 0L;
        int i = 0;
        long b;
        while (((b = readByte()) & 0x80L) != 0) {
            value |= (b & 0x7F) << i;
            i += 7;
            if (i > 63) {
                throw new IllegalArgumentException("Variable length quantity is too long");
            }
        }
        return value | (b << i);
    }
}
