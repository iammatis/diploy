package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.UUIDArraySerializer;

import java.util.UUID;

public class UUIDArraySerializerTest {
    private OutputByteBuffer outputBuffer;
    private UUIDArraySerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new UUIDArraySerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void serializeAndDeserializeTest() {
        UUID value = UUID.randomUUID();
        UUID[] array = new UUID[]{UUID.randomUUID(), UUID.randomUUID()};
        serializer.serialize(outputBuffer, array);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        UUID[] deserialized = serializer.deserialize(inputBuffer);

        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(array[i], deserialized[i]);
        }
    }
}
