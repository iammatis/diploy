package sk.vilk.diploy.buffer;

public interface OutputBuffer {

    int skip(int bytes);

    int writeByte(int value);

    int writeByte(byte value);

    int writeBytes(byte[] value);

    int writeBytes(byte[] source, int offset, int length);

    int insertBytes(byte[] source, int position, int length);

    long insertBufferLength(int position);

    int writeBoolean(boolean value);

    /*
     *
     * Whole numbers
     *
     */

    int writeShort(short value);

    int writeInt(int value);

    int writeLong(long value);

    /*
     *
     * Variable length numbers
     *
     */

    int writeSignedVarInt(int value);

    int writeUnsignedVarInt(int value);

    int writeSignedVarLong(long value);

    int writeUnsignedVarLong(long value);

    /**
     *
     * Decimal numbers
     *
     */

    int writeDouble(double value);

    int writeFloat(float value);

    /*
     *
     * Text
     *
     */

    int writeChar(char value);

    int writeString(String value);
}
