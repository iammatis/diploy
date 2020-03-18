package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.CharSerializer;

public class CharSerializerTest {
    private OutputByteBuffer outputBuffer;
    private CharSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new CharSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(1, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Character.class, serializer.type());
    }

    @Test
    public void serializeAndDeserializeTest() {
        Character value = 'a';
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Character deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
