package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.FloatSerializer;

public class FloatSerializerTest {
    private OutputByteBuffer outputBuffer;
    private FloatSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new FloatSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(4, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Float.class, serializer.type());
    }

    @Test
    public void smallPositiveFloatTest() {
        Float value = 300F;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Float deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigPositiveFloatTest() {
        Float value = Float.MAX_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Float deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void smallNegativeFloatTest() {
        Float value = -300F;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Float deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigNegativeFloatTest() {
        Float value = Float.MIN_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Float deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
