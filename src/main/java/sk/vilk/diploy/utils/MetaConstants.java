package sk.vilk.diploy.utils;

public interface MetaConstants {

    static final Integer CLASS_ID_SIZE = 4;
    static final String CHARSET = "UTF-8";


    static final byte FIELDS_BYTES_START = 125;
    static final byte RELATIONS_BYTES_START = 126;
    static final byte ENTITY_BYTES_END = 127;

    static final byte BYTE_VALUE_INTEGER = 0;
    static final byte BYTE_VALUE_STRING = 1;

    static final byte ONE_TO_ONE = 1;
    static final byte ONE_TO_MANY = 2;
    static final byte MANY_TO_MANY = 3;

}
