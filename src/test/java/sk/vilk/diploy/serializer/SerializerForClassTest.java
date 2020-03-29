package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sk.vilk.diploy.serializers.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class SerializerForClassTest {

    private HashMap<Class, Class> classes = new HashMap<>();

    @BeforeTest
    public void setup() {
        classes.put(short.class, ShortSerializer.class);
        classes.put(Short.class, ShortSerializer.class);
//        classes.put(int.class, IntegerSerializer.class);
        classes.put(Integer.class, IntegerSerializer.class);
        classes.put(long.class, LongSerializer.class);
        classes.put(Long.class, LongSerializer.class);
        classes.put(double.class, DoubleSerializer.class);
        classes.put(Double.class, DoubleSerializer.class);
        classes.put(float.class, FloatSerializer.class);
        classes.put(Float.class, FloatSerializer.class);

        classes.put(char.class, CharSerializer.class);
        classes.put(Character.class, CharSerializer.class);
        classes.put(String.class, StringSerializer.class);

        classes.put(boolean.class, BooleanSerializer.class);
        classes.put(Boolean.class, BooleanSerializer.class);

        classes.put(UUID.class, UUIDSerializer.class);
        classes.put(Date.class, DateSerializer.class);
    }

    @Test
    public void serializerForClassTest() {
        for (Entry<Class, Class> entry: classes.entrySet()) {
            Serializer serializer = SerializerForClass.get(entry.getKey());
            Assert.assertEquals(entry.getValue(), serializer.getClass());
        }
    }
}
