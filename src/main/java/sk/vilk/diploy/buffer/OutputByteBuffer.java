package sk.vilk.diploy.buffer;

import java.util.Arrays;

public class OutputByteBuffer implements OutputBuffer {

    private int position;
    private byte[] bytes;
    private int sizeMask;
    private final int DEFAULT_SIZE = 128;

    public OutputByteBuffer() {
        this.position = 0;
        this.bytes = new byte[DEFAULT_SIZE];
        this.sizeMask = 0xFFFFFFFF - (DEFAULT_SIZE - 1);
    }

    public OutputByteBuffer(int size) {
        this.position = 0;
        this.bytes = new byte[size];
        this.sizeMask = 0xFFFFFFFF-(size - 1);
    }

    private void checkSize(int numOfBytes) {
        numOfBytes += position;
        if ((numOfBytes & sizeMask) != 0) {
            resize(numOfBytes);
        }
    }

    private void resize(int size) {
        // TODO: Calculate new size
        size = 0;
        bytes = Arrays.copyOf(bytes, size);
    }


    @Override
    public void writeByte(byte value) {
        checkSize(1);
        bytes[position++] = value;
    }

    @Override
    public void writeByte(int value) {
        writeByte((byte) value);
    }

    @Override
    public void writeBytes(byte[] value) {
        writeBytes(value, 0, value.length);
    }

    @Override
    public void writeBytes(byte[] source, int offset, int length) {
        checkSize(length);
        System.arraycopy(source, offset, bytes, position, length);
        position += length;
    }

    @Override
    public void writeBoolean(boolean value) {
        checkSize(1);
        bytes[position++] = (byte) (value ? 1 : 0);
    }

    @Override
    public void writeShort(short value) {
        checkSize(2);
        bytes[position++] = (byte) (0xff & (value >> 8));
        bytes[position++] = (byte) (0xff & value);
    }

    @Override
    public void writeInt(int value) {
        writeSignedVarInt(value);
    }

    @Override
    public void writeLong(long value) {
        writeSignedVarLong(value);
    }

    @Override
    public void writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
    }

    @Override
    public void writeFloat(float value) {
        writeInt(Float.floatToIntBits(value));
    }

    @Override
    public void writeChar(char value) {
        bytes[position++] = (byte) (value >> 8);
        bytes[position++] = (byte) value;
    }

    @Override
    public void writeString(String value) {
        int length = value.length();
        writeUnsignedVarInt(length);
        writeBytes(value.getBytes());
    }

    public byte[] getBytes() {
        return bytes;
    }


    /*
        VARINT
     */

    public void writeSignedVarInt(int value) {
        writeUnsignedVarInt((value << 1) ^ (value >> 31));
    }

    public void writeUnsignedVarInt(int value) {
        while ((value & 0xFFFFFF80) != 0L) {
            writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        writeByte(value & 0x7F);
    }

    public void writeSignedVarLong(long value)  {
        writeUnsignedVarLong((value << 1) ^ (value >> 63));
    }

    public void writeUnsignedVarLong(long value) {
        while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
            writeByte(((int) value & 0x7F) | 0x80);
            value >>>= 7;
        }
        writeByte((int) value & 0x7F);
    }
}
