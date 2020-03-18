package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.DateSerializer;

import java.util.Date;

public class DateSerializerTest {
    private OutputByteBuffer outputBuffer;
    private DateSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new DateSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Date.class, serializer.type());
    }

    @Test
    public void serializeAndDeserializeTest() {
        Date value = new Date();
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Date deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
