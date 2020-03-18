package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.ShortSerializer;

public class ShortSerializerTest {
    private OutputByteBuffer outputBuffer;
    private ShortSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new ShortSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(2, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Short.class, serializer.type());
    }

    @Test
    public void smallPositiveShortTest() {
        Short value = 300;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Short deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigPositiveShortTest() {
        Short value = Short.MAX_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Short deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void smallNegativeShortTest() {
        Short value = -300;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Short deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigNegativeShortTest() {
        Short value = Short.MIN_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Short deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
