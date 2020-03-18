package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.UUIDSerializer;

import java.util.UUID;

public class UUIDSerializerTest {
    private OutputByteBuffer outputBuffer;
    private UUIDSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new UUIDSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(16, serializer.size());
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(UUID.class, serializer.type());
    }

    @Test
    public void serializeAndDeserializeTest() {
        UUID value = UUID.randomUUID();
        serializer.serialize(outputBuffer, value);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        UUID deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, value);
    }
}
