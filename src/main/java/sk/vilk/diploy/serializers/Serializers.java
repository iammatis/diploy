package sk.vilk.diploy.serializers;

import java.util.Date;
import java.util.UUID;

public class Serializers {

    public static Serializer<Short> SHORT = new ShortSerializer();
    public static Serializer<Integer> INTEGER = new IntegerSerializer();
    public static Serializer<Long> LONG = new LongSerializer();
    public static Serializer<Double> DOUBLE = new DoubleSerializer();
    public static Serializer<Float> FLOAT = new FloatSerializer();

    public static Serializer<Boolean> BOOLEAN = new BooleanSerializer();

    public static Serializer<Character> CHAR = new CharSerializer();
    public static Serializer<String> STRING = new StringSerializer();

    public static Serializer<UUID> UUID = new UUIDSerializer();
    public static Serializer<Date> DATE = new DateSerializer();
}
