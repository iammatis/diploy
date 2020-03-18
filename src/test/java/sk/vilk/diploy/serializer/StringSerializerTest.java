package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.StringSerializer;

public class StringSerializerTest {
    private OutputByteBuffer outputBuffer;
    private StringSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new StringSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(String.class, serializer.type());
    }

    @Test
    public void serializeAndDeserializeTest() {
        String value = "alphabet";
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        String deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
