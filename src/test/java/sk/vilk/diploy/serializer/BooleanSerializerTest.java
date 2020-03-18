package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.BooleanSerializer;

public class BooleanSerializerTest {
    private OutputByteBuffer outputBuffer;
    private BooleanSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new BooleanSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(1, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Boolean.class, serializer.type());
    }

    @Test
    public void trueBooleanTest() {
        Boolean value = true;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Boolean deserialized = serializer.deserialize(inputBuffer);
        Assert.assertTrue(deserialized);
    }

    @Test
    public void falseBooleanTest() {
        Boolean value = false;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Boolean deserialized = serializer.deserialize(inputBuffer);
        Assert.assertFalse(deserialized);
    }
}
