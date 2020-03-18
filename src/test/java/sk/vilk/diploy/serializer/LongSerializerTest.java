package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.DoubleSerializer;
import sk.vilk.diploy.serializers.LongSerializer;

import java.util.Arrays;

public class LongSerializerTest {

    private OutputByteBuffer outputBuffer;
    private LongSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new LongSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(8, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Long.class, serializer.type());
    }

    @Test
    public void smallPositiveLongTest() {
        Long value = 300L;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Long deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigPositiveLongTest() {
        Long value = Long.MAX_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Long deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void smallNegativeLongTest() {
        Long value = -300L;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Long deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigNegativeLongTest() {
        Long value = Long.MIN_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Long deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
