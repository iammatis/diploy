package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.DoubleSerializer;

public class DoubleSerializerTest {

    private OutputByteBuffer outputBuffer;
    private DoubleSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new DoubleSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(8, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Double.class, serializer.type());
    }

    @Test
    public void smallPositiveDoubleTest() {
        Double value = 300.50;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Double deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigPositiveDoubleTest() {
        Double value = Double.MAX_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Double deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void smallNegativeDoubleTest() {
        Double value = -300.50;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Double deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }

    @Test
    public void bigNegativeDoubleTest() {
        Double value = Double.MIN_VALUE;
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Double deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
