package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.IntegerSerializer;

public class IntegerSerializerTest {
    private OutputByteBuffer outputBuffer;
    private IntegerSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new IntegerSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(4, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Integer.class, serializer.type());
    }

    @Test
    public void smallPositiveIntegerTest() {
        Integer value = 300;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Integer deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigPositiveIntegerTest() {
        Integer value = Integer.MAX_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Integer deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void smallNegativeIntegerTest() {
        Integer value = -300;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Integer deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigNegativeIntegerTest() {
        Integer value = Integer.MIN_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Integer deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
