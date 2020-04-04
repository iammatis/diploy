package sk.vilk.diploy.buffer;

import java.util.Arrays;

public class OutputByteBuffer implements OutputBuffer {

    private int position;
    private byte[] bytes;
    private int sizeMask;
    private int length;
    private static final int DEFAULT_SIZE = 128;

    public OutputByteBuffer() {
        this(DEFAULT_SIZE);
    }

    public OutputByteBuffer(int size) {
        this.position = 0;
        this.length = 0;
        this.bytes = new byte[size];
        this.sizeMask = 0xFFFFFFFF - (size - 1);
    }

    private void checkSize(int numOfBytes) {
        length += numOfBytes;
        numOfBytes += position;
        if ((numOfBytes & sizeMask) != 0) {
            resize(numOfBytes);
        }
    }

    private void resize(int size) {
        int newSize =  Math.max(nextPowTwo(size), length);
        sizeMask = 0xFFFFFFFF - (newSize - 1);
        bytes = Arrays.copyOf(bytes, newSize);
    }

    @Override
    public int skip(int bytes) {
        int currentPosition = position;
        checkSize(bytes);
        position += bytes;
        return currentPosition;
    }

    @Override
    public int writeByte(byte value) {
        checkSize(1);
        bytes[position++] = value;

        return 1;
    }

    @Override
    public int writeByte(int value) {
        writeByte((byte) value);

        return 1;
    }

    @Override
    public int writeBytes(byte[] value) {
        int length = value.length;
        writeBytes(value, 0, length);

        return length;
    }

    @Override
    public int writeBytes(byte[] source, int offset, int length) {
        checkSize(length);
        System.arraycopy(source, offset, bytes, position, length);
        position += length;

        return length;
    }

    @Override
    public int insertBytes(byte[] source, int position, int length) {
        checkSize(length);
        System.arraycopy(source, 0, bytes, position, length);

        return length;
    }

    @Override
    public long insertBufferLength(int position) {
        long v = bytes.length;
        byte[] buf = new byte[8];
        int pos = 0;

        buf[pos++] = (byte) (0xff & (v >> 56));
        buf[pos++] = (byte) (0xff & (v >> 48));
        //$DELAY$
        buf[pos++] = (byte) (0xff & (v >> 40));
        buf[pos++] = (byte) (0xff & (v >> 32));
        buf[pos++] = (byte) (0xff & (v >> 24));
        //$DELAY$
        buf[pos++] = (byte) (0xff & (v >> 16));
        buf[pos++] = (byte) (0xff & (v >> 8));
        buf[pos] = (byte) (0xff & (v));

//        int index = 0;
//        for (int i = 56; i >= 8; i -= 8) {
//            longBytes[index++] = (byte) (0xFF & (bufferLength >> i));
//        }
//        longBytes[index] = (byte) (0xFF & bufferLength);
//
        insertBytes(buf, position, 8);
        return v;
    }

    @Override
    public int writeBoolean(boolean value) {
        checkSize(1);
        bytes[position++] = (byte) (value ? 1 : 0);

        return 1;
    }

    @Override
    public int writeShort(short value) {
        checkSize(2);
        bytes[position++] = (byte) (0xff & (value >> 8));
        bytes[position++] = (byte) (0xff & value);

        return 2;
    }

    @Override
    public int writeInt(int value) {
        checkSize(4);
        bytes[position++] = (byte) (0xff & (value >> 24));
        bytes[position++] = (byte) (0xff & (value >> 16));
        bytes[position++] = (byte) (0xff & (value >> 8));
        bytes[position++] = (byte) (0xff & value);

        return 4;
    }

    @Override
    public int writeLong(long value) {
        checkSize(8);
        bytes[position++] = (byte) (0xff & (value >> 56));
        bytes[position++] = (byte) (0xff & (value >> 48));
        bytes[position++] = (byte) (0xff & (value >> 40));
        bytes[position++] = (byte) (0xff & (value >> 32));
        bytes[position++] = (byte) (0xff & (value >> 24));
        bytes[position++] = (byte) (0xff & (value >> 16));
        bytes[position++] = (byte) (0xff & (value >> 8));
        bytes[position++] = (byte) (0xff & value);

        return 8;
    }

    @Override
    public int writeDouble(double value) {
        return writeSignedVarLong(Double.doubleToLongBits(value));
    }

    @Override
    public int writeFloat(float value) {
        return writeSignedVarInt(Float.floatToIntBits(value));
    }

    @Override
    public int writeChar(char value) {
        checkSize(2);
        bytes[position++] = (byte) (value >> 8);
        bytes[position++] = (byte) value;

        return 2;
    }

    @Override
    public int writeString(String value) {
        int size = this.length;
        int length = value.length();
        writeUnsignedVarInt(length);
        writeBytes(value.getBytes());

        return this.length - size;
    }


    /**
     *
     * Variable length number
     *
     */

    public int writeSignedVarInt(int value) {
        return writeUnsignedVarInt((value << 1) ^ (value >> 31));
    }

    public int writeUnsignedVarInt(int value) {
        int size = this.length;
        while ((value & 0xFFFFFF80) != 0L) {
            writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        writeByte(value & 0x7F);

        return this.length - size;
    }

    public int writeSignedVarLong(long value)  {
       return writeUnsignedVarLong((value << 1) ^ (value >> 63));
    }

    public int writeUnsignedVarLong(long value) {
        int size = this.length;
        while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
            writeByte(((int) value & 0x7F) | 0x80);
            value >>>= 7;
        }
        writeByte((int) value & 0x7F);

        return this.length - size;
    }

    /**
     * Accessors
     * @return byte[]
     */

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] toBytes() {
        return slice(bytes, 0, position);
    }

    private byte[] slice(byte[] bytes, int start, int end) {
        int length = bytes.length;

        if (end > length) {
            System.out.println("Too big");
//            throw new Exception("Too big");
        }

        if (start > end) {
            System.out.println("Too far");
        }

        int newLength = end - start;
        byte[] newBytes = new byte[newLength];
        System.arraycopy(bytes, start, newBytes, 0, newLength);
        return newBytes;
    }

    private int nextPowTwo(final int a) {
        return 1 << (32 - Integer.numberOfLeadingZeros(a - 1));
    }
}
