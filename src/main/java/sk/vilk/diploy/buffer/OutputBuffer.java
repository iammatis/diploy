package sk.vilk.diploy.buffer;

import java.io.OutputStream;

public interface OutputBuffer {

    void writeByte(int value);

    void writeByte(byte value);

    public void writeBytes(byte[] value);

    public void writeBytes(byte[] source, int offset, int length);

    void writeBoolean(boolean value);

    /**
     *
     * Whole numbers
     *
     */

    void writeShort(short value);

    void writeInt(int value);

    void writeLong(long value);

    /**
     *
     * Decimal numbers
     *
     */

    void writeDouble(double value);

    void writeFloat(float value);

    /**
     *
     * Text
     *
     */

    void writeChar(char value);

    void writeString(String value);
}
