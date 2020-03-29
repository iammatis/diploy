package sk.vilk.diploy.buffer;

public interface InputBuffer {

    byte readByte();

    boolean readBoolean();

    short readShort();

    int readInt();

    long readLong();

    int readSignedVarInt();

    int readUnsignedVarInt();

    long readSignedVarLong();

    long readUnsignedVarLong();

    double readDouble();

    float readFloat();

    char readChar();

    String readString();

}
