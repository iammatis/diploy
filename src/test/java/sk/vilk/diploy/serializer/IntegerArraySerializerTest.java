package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.IntegerArraySerializer;

public class IntegerArraySerializerTest {
    private OutputByteBuffer outputBuffer;
    private IntegerArraySerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new IntegerArraySerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Integer[].class, serializer.type());
    }

    @Test
    public void arrayTest() {
        Integer[] array = {1, Integer.MIN_VALUE, 2, Integer.MAX_VALUE, 3};
        serializer.serialize(outputBuffer, array);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Integer[] deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, array);
    }
}
